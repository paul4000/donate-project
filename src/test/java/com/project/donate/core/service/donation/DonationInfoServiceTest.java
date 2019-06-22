package com.project.donate.core.service.donation;

import com.google.common.collect.Lists;
import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.User;
import com.project.donate.core.repositories.UsersRepository;
import com.project.donate.core.service.project.ProjectsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DonationInfoServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    ProjectsService projectsService;
    @Spy
    Web3jServiceSupplier web3jServiceSupplier;

    @InjectMocks
    DonationInfoService donationInfoService;

    @Test
    public void test() {

        //given
        when(usersRepository.findByUsername(any())).thenReturn(getUser());
        when(projectsService.getAllProjects()).thenReturn(getProjects());

        //when
        List<Long> userDonatedProjects = donationInfoService.getUserDonatedProjects("DONATOR1");

        //then
        assertThat(userDonatedProjects).isNotEmpty();
    }

    private List<Project> getProjects() {

        Project project1 = new Project();
        project1.setAddress("0x275b73cb9cc04ba38191335f0102e8cc5220b73d");

        Project project2 = new Project();
        project2.setAddress("0x24e6a02d22217d4feb03943a7096ad7fa30ceff8");

        return Lists.newArrayList(project1, project2);
    }

    private User getUser() {
        User u = new User();
        u.setAccount("0x401797f625488acd45ed49572563e333b0a978c6");
        return u;
    }

}