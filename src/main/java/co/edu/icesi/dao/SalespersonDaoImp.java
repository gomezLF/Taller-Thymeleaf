package co.edu.icesi.dao;

import co.edu.icesi.model.sales.Salesperson;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@Scope("singleton")
public class SalespersonDaoImp implements Dao<Salesperson>{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Salesperson findById(Integer id) {
        return entityManager.find(Salesperson.class, id);
    }

    @Override
    @Transactional
    public void save(Salesperson salesperson) {
        entityManager.persist(salesperson);
    }

    @Override
    @Transactional
    public void update(Salesperson salesperson) {
        entityManager.merge(salesperson);
    }

    @Override
    @Transactional
    public void delete(@NotNull Salesperson salesperson) {
        Optional<Salesperson> optional = Optional.ofNullable(findById(salesperson.getBusinessentityid()));

        optional.ifPresent(value -> executeInsideTransaction(entityManager -> entityManager.remove(value)));
    }

    @Override
    public List<Salesperson> findAll() {
        String query = "SELECT sp FROM Salesperson sp";
        return entityManager.createQuery(query).getResultList();
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        try {
            action.accept(entityManager);
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
}
