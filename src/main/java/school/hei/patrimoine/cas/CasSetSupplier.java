package school.hei.patrimoine.cas;

import school.hei.patrimoine.cas.pro3.BakoCaseStudy;
import school.hei.patrimoine.cas.pro3.TianaCaseStudy;
import school.hei.patrimoine.modele.Personne;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static school.hei.patrimoine.modele.Argent.ariary;

public class CasSetSupplier implements Supplier<CasSet> {
  @Override
  public CasSet get() {
    var caseStudyStartDate = LocalDate.of(2025, 4, 8);
    var bakoCaseStudyEndDate = LocalDate.of(2025, 12, 31);
    var bakoCaseStudy = new BakoCaseStudy(caseStudyStartDate, bakoCaseStudyEndDate, Map.of(new Personne("Bako"), 1.));

    var tianaCaseStudyEndDate = LocalDate.of(2026, 3, 31);
    var tianaCaseStudy = new TianaCaseStudy(caseStudyStartDate, tianaCaseStudyEndDate, Map.of(new Personne("Tiana"), 1.));

    return new CasSet(Set.of(bakoCaseStudy), ariary(13_111_657));
  }
}
