package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class DataLoaderMySql implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoaderMySql(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryRepository.count() == 0) {
            log.debug("Loading categories");
            loadCategories();
        }
        
        if (unitOfMeasureRepository.count() == 0) {
            log.debug("Loading UOMs");
            loadUoms();
        }
    }

    private void loadUoms() {
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Teaspoon").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Tablespoon").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Cup").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Pinch").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Ounce").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Each").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Clove").build()
        );
        unitOfMeasureRepository.save(
            UnitOfMeasure.builder().description("Pint").build()
        );
    }

    private void loadCategories() {
        categoryRepository.save(
            Category.builder().description("American").build()
        );
        categoryRepository.save(
            Category.builder().description("Italian").build()
        );
        categoryRepository.save(
            Category.builder().description("Mexican").build()
        );
        categoryRepository.save(
            Category.builder().description("Fast Food").build()
        );
    }
}
