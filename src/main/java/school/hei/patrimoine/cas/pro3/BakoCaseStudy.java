package school.hei.patrimoine.cas.pro3;

import school.hei.patrimoine.cas.Cas;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

public class BakoCaseStudy extends Cas {
    private final Compte bni;
    private final Compte bmoi;
    private final Compte safeBox;
    private final LocalDate caseStudyDate;
    private final LocalDate caseStudyEndDate;

    public BakoCaseStudy(LocalDate ajd, LocalDate finSimulation, Map<Personne, Double> possesseurs) {
        super(ajd, finSimulation, possesseurs);
        caseStudyDate = LocalDate.of(2025, 4, 8);
        caseStudyEndDate = LocalDate.of(2025, 12, 31);
        bni = new Compte("BNI account", caseStudyDate, ariary(2_000_000));
        bmoi = new Compte("BMOI saving account", caseStudyDate, ariary(625_000));
        safeBox = new Compte("Home safety-box", caseStudyDate, ariary(1_750_000));
    }

    @Override
    protected Devise devise() {
        return MGA;
    }

    @Override
    protected String nom() {
        return "Bako case study";
    }

    @Override
    protected void init() {

    }

    @Override
    protected void suivi() {

    }

    @Override
    public Set<Possession> possessions() {
        // Flux BNI
        new FluxArgent("CDI salary", bni, caseStudyDate, caseStudyEndDate, 2, ariary(2_125_000));
        new FluxArgent("Saving for bmoi", bni, caseStudyDate, caseStudyEndDate, 3, ariary(-200_000));
        new FluxArgent("Rent", bni, caseStudyDate, caseStudyEndDate, 26, ariary(-600_000));
        new FluxArgent("Basic needs", bni, caseStudyDate, caseStudyEndDate, 1, ariary(-700_000));

        // Flux BMOI
        new FluxArgent("Deposit from bni", bmoi, caseStudyDate, caseStudyEndDate, 3, ariary(200_000));

        var laptop = new Materiel("Personal computer", caseStudyDate, caseStudyDate, ariary(3_000_000), -0.12);

        return Set.of(bni, bmoi, safeBox, laptop);
    }
}
