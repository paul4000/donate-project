package com.project.donate.core.service.donation;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.User;
import com.project.donate.core.repositories.UsersRepository;
import com.project.donate.core.service.project.ProjectsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DonationInfoService {

    private final Logger logger = Logger.getLogger(DonationInfoService.class);
    private UsersRepository usersRepository;
    private Web3jServiceSupplier web3jServiceSupplier;
    private ProjectsService projectsService;

    @Autowired
    public DonationInfoService(UsersRepository usersRepository, Web3jServiceSupplier web3jServiceSupplier, ProjectsService projectsService) {
        this.usersRepository = usersRepository;
        this.web3jServiceSupplier = web3jServiceSupplier;
        this.projectsService = projectsService;
    }

    public List<Long> getUserDonatedProjects(String username) {

        User byUsername = usersRepository.findByUsername(username);
        String account = byUsername.getAccount();

        Event donateEvent = new Event("DonateProject", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {},
                new TypeReference<Utf8String>(false) {}));


        return retrieveAllDonatedIdsByLog(account, donateEvent);
    }

    private List<Long> retrieveAllDonatedIdsByLog(String userAccount, Event donateEvent) {

        Stream<String> projectAddresses = projectsService.getAllProjects()
                .stream()
                .map(Project::getAddress)
                .filter(Objects::nonNull);

        String donateEventHash = EventEncoder.encode(donateEvent);

        List<Long> donatedProjects = new ArrayList<>();

        projectAddresses
                .forEach(projectAddress -> {

                    EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                            DefaultBlockParameterName.LATEST, projectAddress);

                    web3jServiceSupplier
                            .getWeb3j()
                            .ethLogFlowable(filter)
                            .subscribe(log -> {
                                        String eventHash = log.getTopics().get(0);
                                        if (eventHash.equals(donateEventHash)) {

                                            Address donator = (Address) FunctionReturnDecoder.decodeIndexedValue(log.getTopics().get(1),
                                                    new TypeReference<Address>() {
                                                    });

                                            if (userAccount.equals(donator.toString())) {

                                                List<Type> results = FunctionReturnDecoder.decode(
                                                        log.getData(), donateEvent.getNonIndexedParameters());

                                                String id = results.get(0).toString();

                                                Long projectID = Long.valueOf(id);
                                                donatedProjects.add(projectID);
                                            }
                                        }
                                    }
                            );
                });

        return donatedProjects
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

}
