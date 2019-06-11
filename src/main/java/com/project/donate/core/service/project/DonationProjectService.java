package com.project.donate.core.service.project;

import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.exceptions.blockchain.DonationException;
import com.project.donate.core.exceptions.blockchain.HandlingProjectException;
import com.project.donate.core.model.Project;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class DonationProjectService extends AbstractProjectService {

    private final static Logger logger = Logger.getLogger(DonationProjectService.class);


    public DonationProjectService(ProjectsService projectsService, Web3jServiceSupplier web3jServiceSupplier,
                                  SecurityService securityService, UserService userService) {

        super(userService, securityService, projectsService, web3jServiceSupplier);

    }

    public void donateProject(String passForWallet, long projectId, double donationValue) {

        Credentials donatorCredentials = getCredentials(passForWallet);

        com.project.donate.core.blockchain.Project projectForDonate = loadProjectFromBlockchain(projectId, donatorCredentials);

        BigDecimal goalWei = Convert.toWei(String.valueOf(donationValue), Convert.Unit.ETHER);

        try {

            TransactionReceipt sendProjectDonationResult = projectForDonate.makeDonation(goalWei.toBigInteger()).send();

            logger.info(sendProjectDonationResult.toString());

        } catch (Exception e) {

            e.printStackTrace();

            throw new DonationException();
        }
    }

    public void voteForExecution(long id, int value, String wallPass) {

        Credentials donatorCredentials = getCredentials(wallPass);

        com.project.donate.core.blockchain.Project projectForVoting = loadProjectFromBlockchain(id, donatorCredentials);

        try {
            TransactionReceipt receipt = projectForVoting.voteForProjectExecution(new BigInteger(String.valueOf(value))).send();
            logger.info(receipt.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new HandlingProjectException("Cannot vote for project execution");
        }

    }
}
