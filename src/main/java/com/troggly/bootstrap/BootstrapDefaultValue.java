package com.troggly.bootstrap;

import com.troggly.apiObject.UserApi;
import com.troggly.apiObject.UserDetailsApi;
import com.troggly.enums.Role;
import com.troggly.enums.Type;
import com.troggly.mapper.UserMapper;
import com.troggly.model.*;
import com.troggly.repository.*;
import com.troggly.service.EmailHtmlSender;
import com.troggly.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.*;

/**
 * Created by Vlad on 10.05.2017.
 */
@Service
@PropertySource("classpath:foo.properties")
@PropertySource("classpath:too.properties")
public class BootstrapDefaultValue  implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BootstrapDefaultValue.class);

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailHtmlSender emailHtmlSender;


    @Transactional()
    @Override
    public void afterPropertiesSet() throws Exception {
        createSystemUsers();
        createSimplePortfolio();
       // test();
   //     emailService.sendSimpleMessage("sistimas001@gmail.com","ololo","trololo");
//        emailService.send();
        Context context = new Context();
        context.setVariable("title", env.getProperty("fora"));
        context.setVariable("description", env.getProperty("sora"));

        emailHtmlSender.send(null, "Title of email", "templates/email/feedback-template.html", context);
    }

    private void createSystemUsers(){
        User user = new User();
        user.setPasswordHash("$2a$06$ptSH.gR7OB6tmo2yzX8.Cu1khpWmkGHSu/pGfCcRrV0NxO13H.WqG");//12345
        user.setDate(new Date());
        user.setLogin("admin");
        user.setType(Type.PRIVATE_PERSON);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        roles.add(Role.ROLE_MANAGER);
        roles.add(Role.ROLE_USER);

        user.setRoles(roles);

        UserDetails userDetails = new UserDetails();
        userDetails.setSecondName("Matrosov");
        userDetails.setFirstName("Vladyslav");
        userDetails.setAddress("Chernihiv");
        userDetails.setId(user.getLogin()+"_details");

        user.setUserDetails(userDetails);
       // userDetailsRepository.save(userDetails);
        userRepository.save(user);
        logger.info("Added default users");

//        UserApi userApi = new UserApi();
//        userApi.login = "admin";
//        userApi.date = new Date();
//                List<String> roles = new ArrayList<>();
//        roles.add(Role.ROLE_ADMIN.name());
//        roles.add(Role.ROLE_MANAGER.name());
//        roles.add(Role.ROLE_USER.name());
//        userApi.roles = roles;
//        userApi.type = Type.PRIVATE_PERSON.name();
//        userApi.userDetails = null;
//        User user = userMapper.toInternal(userApi);
//        logger.info(user.toString());
//     //   user.setPasswordHash("$2a$06$ptSH.gR7OB6tmo2yzX8.Cu1khpWmkGHSu/pGfCcRrV0NxO13H.WqG");
//        userRepository.save(user);

    }

   private void createSimplePortfolio(){
//        Image image1 = new Image();
//        image1.setDate(new Date());
//       image1.setLink("testLink1");
//       image1.setName("testName1");
//       Image image2 = new Image();
//       image2.setDate(new Date());
//       image2.setLink("testLink2");
//       image2.setName("testName2");
//
//       Portfolio portfolio = new Portfolio();
////       portfolio.setId(5555l);
//       portfolio.setDescription("Quisque laoreet orci eu felis mollis accumsan. Praesent id augue sapien. Praesent eu hendrerit est. Quisque imperdiet lacus tincidunt ante imperdiet, aliquet luctus odio volutpat. Donec tincidunt elit non tellus bibendum, eget pharetra quam faucibus.");
//       portfolio.setDate(new Date());
//       portfolio.setProjectReference("testProjRef");
//       portfolio.setTechnology("teh1,teh2");
//       portfolio.setFullName("testFullName");
//       portfolio.setDevelopmentTime("Two weeks");
//       portfolio.setProjectType("project super type");
//       portfolio.setMainImage(image1);
//      //portfolioRepository.save(portfolio);
////       portfolio.setId(new Random().nextLong());
//       Set<Image> images = new HashSet<>();
////       image1.setPortfolio(portfolio);
////       image2.setPortfolio(portfolio);
//       images.add(image1);
//       images.add(image2);
//      portfolio.setImages(images);

      for(int i = 0;i<5;i++){
          portfolioRepository.save(createTestPart(""+i,"Site"));
      }
       for(int i = 5;i<10;i++){
           portfolioRepository.save(createTestPart(""+i,"System"));
       }
       logger.info("Added default portfolio");

       for (String srt : portfolioRepository.findDistinctProjectType()){
           logger.info("Type : "+srt);
           logger.info("values : "+portfolioRepository.findAllByProjectType(srt).size());
       }
    }
    public Portfolio createTestPart(String text, String type){
        Image image1 = new Image();
        image1.setDate(new Date());
        image1.setLink("http://localhost/testMainImage.jpg");
        image1.setName("testName "+text);
        Image image2 = new Image();
        image2.setDate(new Date());
        image2.setLink("http://localhost/713984bb31385c2d7a7b18979942916b.jpg");
        image2.setName("testName "+text);


        //

        // image1.setLink("https://graffitiartpedia.com/wp-content/uploads/2017/08/cool-hd-ass-logo-wallpaper-1080p-hd-wallpapers.jpg");
        // image2.setLink("http://rwrant.co.za/wp-content/uploads/2013/04/Hannah-Davis-04.jpg");
        //



        Portfolio portfolio = new Portfolio();
//       portfolio.setId(5555l);
        portfolio.setDescription("Quisque laoreet orci eu felis mollis accumsan. Praesent id augue sapien. Praesent eu hendrerit est. Quisque imperdiet lacus tincidunt ante imperdiet, aliquet luctus odio volutpat. Donec tincidunt elit non tellus bibendum, eget pharetra quam faucibus.");
        portfolio.setDate(new Date());
        portfolio.setProjectReference("testProjRef");
        portfolio.setTechnology("teh1,teh2 "+text);
        portfolio.setFullName("testFullName "+text);
        portfolio.setDevelopmentTime("Two weeks");
        portfolio.setProjectType(type);
        portfolio.setMainImage(image1);
        //portfolioRepository.save(portfolio);
//       portfolio.setId(new Random().nextLong());
        Set<Image> images = new HashSet<>();
//       image1.setPortfolio(portfolio);
//       image2.setPortfolio(portfolio);
        images.add(image1);
        images.add(image2);
        portfolio.setImages(images);
        return portfolio;
     //   portfolioRepository.save(portfolio);
    }

//     public void test(){
////         Phone phone1 = new Phone();
////         Phone phone2 = new Phone();
////         Employee employee = new Employee();
////         List<Phone> list = new ArrayList<>();
////         list.add(phone1);
////         list.add(phone2);
////         employee.setPhones(list);
////         employeeRepository.save(employee);
//
//
//         // save a couple of categories
//         BookCategory categoryA = new BookCategory("Category A");
//         Set bookAs = new HashSet<Book>(){{
//             add(new Book("Book A1", categoryA));
//             add(new Book("Book A2", categoryA));
//             add(new Book("Book A3", categoryA));
//         }};
//         categoryA.setBooks(bookAs);
//
//         BookCategory categoryB = new BookCategory("Category B");
//         Set bookBs = new HashSet<Book>(){{
//             add(new Book("Book B1", categoryB));
//             add(new Book("Book B2", categoryB));
//             add(new Book("Book B3", categoryB));
//         }};
//         categoryB.setBooks(bookBs);
//         bookCategoryRepository.save(categoryA);
////         bookCategoryRepository.save(new HashSet<BookCategory>() {{
////             add(categoryA);
////             add(categoryB);
////         }});
//
//     }
}
