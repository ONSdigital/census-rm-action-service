package uk.gov.ons.ctp.response.caseframe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.gov.ons.ctp.response.caseframe.domain.model.Category;

/**
 * JPA Data Repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
  /**
   * To find a category by name
   * @param name the name of the category
   * @return the found category
   */
  Category findByName(String name);
}
