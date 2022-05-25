package com.ca.formation.formationdemo1;

import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;

@SpringBootApplication
public class FormationDemo1Application {

    @Value("${mon.application.travail}")
    String monApplication;

    @Value("${mon.application.lieu}")
    String lieuFormation;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(FormationDemo1Application.class, args);
    }

    @Bean
    public void addBean(){
        System.out.println(monApplication);
        System.out.println(" Démarrage application Spring Boot");
    }



    @Bean
    public CommandLineRunner demo(PersonneRepository repository){
        return (args -> {
            Personne personne1 = repository.save(new Personne("Lacroix", "Jean", 20));
            repository.save(new Personne("Beau", "Michel", 30));
            repository.save(new Personne("Abdel", "Moussa", 40));

           // System.out.println(repository.count());

            repository.delete(personne1);

            List<Personne> personneList = repository.findByNomAndPrenom("Abdel", "Moussa");


            //List<Personne> personneList = repository.findNomPrenom2("Abdel", "Moussa");

           personneList.stream().forEach(System.out::println);

           // System.out.println(personneList.size());
        });
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates-new/");
        templateResolver.setSuffix(".html");
        templateResolver.setCheckExistence(true);

        return templateResolver;
    }

}
