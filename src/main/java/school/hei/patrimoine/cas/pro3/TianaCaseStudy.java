package school.hei.patrimoine.cas.pro3;

import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static school.hei.patrimoine.modele.Argent.ariary;

public class TianaCaseStudy extends Cas {
    private final Compte bankAccount;
    private final LocalDate caseStudyDate;
    private final LocalDate caseStudyEndDate;
    private final Materiel tianaLand;

    public TianaCaseStudy(LocalDate ajd, LocalDate finSimulation, Map<Personne, Double> possesseurs) {
        super(ajd, finSimulation, possesseurs);
        caseStudyDate = LocalDate.of(2025, 4, 8);
        caseStudyEndDate = LocalDate.of(2026, 3, 31);
        bankAccount = new Compte("Tiana bank account", caseStudyDate, ariary(60_000_000));
        tianaLand = new Materiel("Built estate", caseStudyDate, caseStudyDate, ariary(100_000_000), 0.10);

    }

    @Override
    protected Devise devise() {
        return Devise.MGA;
    }

    @Override
    protected String nom() {
        return "Tiana Case Study";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void suivi() {

    }

    @Override
    public Set<Possession> possessions() {
                /*1. Un unique compte bancaire qui possède 60_000_000 Ar
        2. Un terrain batî ayant une valeur marchande de 100_000_000 Ar à date, et qui s'apprécie de 10% tous les ans
        3. Une dépense mensuelle de 4_000_000 Ar pour loger et nourir toute sa famille*/
        new FluxArgent("Monthly expense", bankAccount, caseStudyDate, caseStudyEndDate, 1, ariary(-4_000_000));

        /*Son prochain coup entrepreneurial est un projet de 6 mois du 1 juin 2025 au 31 décembre 2025 :
        1. Ce projet lui fera dépenser 5_000_000 Ar tous les mois, à décaisser de sa banque tous les 5 du mois
        2. Ce projet lui rapportera en brut (cad hors dépenses) 70_000_000 Ar à encaisser en 2 fois : 10% 1 mois avant le lancement (cad 1 mai 2025) et 90% restant 1 mois après la fin (cad 31 janvier 2026)
        */
        var projectStartDate = LocalDate.of(2025, 6, 1);
        var projectEndDate = LocalDate.of(2025, 12, 31);
        new FluxArgent("Project monthly expense", bankAccount, projectStartDate, projectEndDate, 5, ariary(-5_000_000));

        var firstShareProfitDate = LocalDate.of(2025, 5, 1);
        var secondShareProfitDate = LocalDate.of(2026, 1, 31);
        var firstShareProfit = ariary((int)(70_000_000 * 0.1));
        var secondShareProfit = ariary((int)(70_000_000 * 0.9));

        new FluxArgent("Project first share profit", bankAccount, firstShareProfitDate, firstShareProfitDate, 5, ariary(7_000_000));
        new FluxArgent("Project first share profit", bankAccount, secondShareProfitDate, secondShareProfitDate, 5, ariary(63_000_000));


        /*1. La banque lui prêtera 20_000_000 Ar le 27 juillet 2025
        2. Tiana remboursera une mensualité de 2_000_000 Ar à compter du 27 août 2025 pendant 12 mois (soit un remboursement total de 24_000_000 Ar)*/

        var loanDateOfEffect = LocalDate.of(2025, 7, 27);
        var loanStartDateOfRepayment = LocalDate.of(2025, 8, 27);
        var loanToRepay = new Dette("Bank loan value", loanDateOfEffect, ariary(-20_000_000));
        new FluxArgent("Loan monthly repayment", bankAccount, loanStartDateOfRepayment, loanStartDateOfRepayment.plusYears(1), 27, ariary(-2_000_000));

        return Set.of(bankAccount, tianaLand, loanToRepay);
    }
}
