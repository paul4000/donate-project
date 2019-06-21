package com.project.donate.core.service.accounts;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.exceptions.WalletOpeningException;
import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.Role;
import com.project.donate.core.model.User;
import com.project.donate.core.model.response.AccountRS;
import com.project.donate.core.repositories.UsersRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.project.donate.core.helpers.PropertiesUtils.getPropertyFromConfig;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class AccountsService {

    private final Logger logger = Logger.getLogger(AccountsService.class);
    private final static String WALLET_PATH = "path.blockchain.keystore";

    private UsersRepository usersRepository;
    private Web3jServiceSupplier web3jServiceSupplier;

    @Autowired
    public AccountsService(UsersRepository usersRepository, Web3jServiceSupplier web3jServiceSupplier) {

        Assert.notNull(usersRepository, "UsersRepository should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");

        this.usersRepository = usersRepository;
        this.web3jServiceSupplier = web3jServiceSupplier;
    }

    public Optional<String> createWalletFile(String password) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        Optional<String> keystorePath = PropertiesUtils.getPropertyFromConfig("path.blockchain.keystore");

        Optional<String> fullNewWalletFile = Optional.empty();

        if (keystorePath.isPresent()) {

            fullNewWalletFile = Optional.of(WalletUtils.generateFullNewWalletFile(password, new File(keystorePath.get())));

        }

        return fullNewWalletFile;
    }

    public void validateIfChosenAddressIsExecutor(String executor) {

        User byAccount = usersRepository.findByAccount(executor);

        if (!byAccount.getRole().equals(Role.EXECUTOR))
            throw new HandlingProjectException("Chosen account is not executor");
    }


    public AccountRS getAccount(String username) {

        User byUsername = usersRepository.findByUsername(username);

        AccountRS response = new AccountRS();
        setAccountBasics(byUsername, response);

        if (byUsername.getRole().equals(Role.INITIATOR)) {
            setProjectStatistics(response, byUsername);
        }

        return response;

    }

    private void setProjectStatistics(AccountRS response, User byUsername) {

        int size = byUsername.getProjects().size();
        response.setNumberOfProject(size);

        List<Project> projectList = byUsername.getProjects().stream()
                .filter(pr -> pr.getAddress() != null)
                .collect(Collectors.toList());

        long countOfSuccessful = projectList.stream()
                .map(project -> TRUE.equals(project.getExecutedWithSuccess()))
                .filter(p -> p)
                .count();

        long countOfFailed = projectList.stream()
                .map(project -> FALSE.equals(project.getExecutedWithSuccess()))
                .filter(p -> p)
                .count();

        response.setNumberOfFailedProjects(countOfFailed);
        response.setNumberOfSuccessfulProjects(countOfSuccessful);

    }


    private void setAccountBasics(User user, AccountRS accountRS) {
        accountRS.setName(user.getName());
        accountRS.setUsername(user.getUsername());
        accountRS.setAccount(user.getAccount());
        accountRS.setEmail(user.getEmail());
        accountRS.setType(user.getRole().toString());
        setAccountBalance(user, accountRS);
    }

    private void setAccountBalance(User user, AccountRS accountRS) {
        try {

            BigInteger balance = web3jServiceSupplier.getWeb3j()
                    .ethGetBalance(user.getAccount(), DefaultBlockParameter.valueOf("latest"))
                    .send()
                    .getBalance();


            BigDecimal balanceInDecimal = Convert.fromWei(balance.toString(), Convert.Unit.ETHER);

            accountRS.setAccountBalance(balanceInDecimal.doubleValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ByteArrayResource getWalletOfUser(String username) throws IOException {

        User byUsername = usersRepository.findByUsername(username);
        String walletFile = byUsername.getWalletFile();

        Optional<String> walletPath = getPropertyFromConfig(WALLET_PATH);

        if (walletPath.isEmpty()) throw new WalletOpeningException();

        String walletPathOpt = walletPath.get();

        String absolutePathToWallet = walletPathOpt + "\\" + walletFile;

        File walletFileCreated = new File(absolutePathToWallet);
        Path path = Paths.get(walletFileCreated.getAbsolutePath());
        return new ByteArrayResource(Files.readAllBytes(path));
    }

}
