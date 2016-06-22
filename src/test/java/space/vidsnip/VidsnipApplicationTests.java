package space.vidsnip;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import space.vidsnip.model.graphuser.GraphUser;
import space.vidsnip.model.graphuser.GraphUserRepository;
import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VidsnipApplication.class)
@WebAppConfiguration
public class VidsnipApplicationTests {

    @Autowired GraphUserRepository graphUserRepository;
    @Autowired UserRepository userRepository;

	@Test
	public void contextLoads() {
		GraphUser marijn = new GraphUser("Marijn");
        graphUserRepository.save(marijn);

        User rene = new User("BusStop");
        userRepository.save(rene);
	}

}
