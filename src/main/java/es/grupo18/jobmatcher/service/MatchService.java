/*

package es.grupo18.jobmatcher.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.grupo18.jobmatcher.model.Company;
import es.grupo18.jobmatcher.model.Match;
import es.grupo18.jobmatcher.model.User;

@Service
public class MatchService {

    private List<Match> matches;
    private final UserService userService;
    private final CompanyService companyService;

    public MatchService(UserService userService, CompanyService companyService) {
        this.matches = new ArrayList<>();
        this.userService = userService;
        this.companyService = companyService;
    }

    public List<Match> getMatches() { // Returns all matches
        return matches;
    }

    public void addMatch(User user, Company company) { // Adds a new match
        Match match = new Match(user, company);
        matches.add(match);
    }

    public boolean toggleMatch(User user, Company company) { // Adds or removes a match
        boolean matchExists = matches.removeIf(match -> match.getUser().equals(user) && match.getCompany().equals(company));

        if (!matchExists) {
            addMatch(user, company);
        }

        return !matchExists;
    }

    public List<Company> getMatchedCompanies(){ // Returns all companies that have been matched
        List<Company> companies = new ArrayList<>();
        for (Match match : matches){
            companies.add(match.getCompany());
        }
        return companies;
    }

    public void loadMatches() { // Loads all matches
        for (Company company : companyService.getCompaniesList()) {
            if (company.getFavoriteUsers().contains(userService.getUser())) {
                Match match = new Match(userService.getUser(), company);
                matches.add(match);
            }
        }
    }
    
}
    
    */