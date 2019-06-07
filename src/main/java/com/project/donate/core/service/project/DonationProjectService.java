package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.exceptions.blockchain.DonationException;
import com.project.donate.core.helpers.PropertiesUtils;
import com.project.donate.core.model.Project;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

@Service
public class DonationProjectService extends AbstractProjectService{

    private final static Logger logger = Logger.getLogger(PropertiesUtils.class);

    private ProjectsService projectsService;
    private Web3jServiceSupplier web3jServiceSupplier;

    public DonationProjectService(ProjectsService projectsService, Web3jServiceSupplier web3jServiceSupplier,
                                  SecurityService securityService, UserService userService) {

        super(userService, securityService);

        Assert.notNull(projectsService, "ProjectsService should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");

        this.projectsService = projectsService;
        this.web3jServiceSupplier = web3jServiceSupplier;
    }

    public void donateProject(String passForWallet, long projectId, int donationValue) {

        Project projectFromDB = projectsService.getProject(projectId);

        Credentials donatorCredentials = getCredentials(passForWallet);

        com.project.donate.core.blockchain.Project projectForDonate =
                com.project.donate.core.blockchain.Project.load(projectFromDB.getAddress(), web3jServiceSupplier.getWeb3j(),
                donatorCredentials, new DefaultGasProvider());

        BigDecimal goalWei = Convert.toWei(String.valueOf(donationValue), Convert.Unit.ETHER);

        try {

            TransactionReceipt sendProjectDonationResult = projectForDonate.makeDonation(goalWei.toBigInteger()).send();

            logger.info(sendProjectDonationResult.toString());

        } catch (Exception e) {

            e.printStackTrace();

            throw new DonationException();
        }
    }


}
