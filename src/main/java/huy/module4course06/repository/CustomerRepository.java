package huy.module4course06.repository;

import huy.module4course06.model.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class CustomerRepository implements ICustomerRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where c.id=:id", Customer.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Customer customer) {
        if (customer.getId() != null) {
            entityManager.merge(customer);
        } else {
//            entityManager.persist(customer);
            saveWithStoreProcedure(customer);
        }
    }

    public void saveWithStoreProcedure(Customer customer) {
        String sql = "CALL Insert_customer(:name,:email,:address)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("name", customer.getName());
        query.setParameter("email", customer.getEmail());
        query.setParameter("address", customer.getAddress());
        query.executeUpdate();
    }

    @Override
    public void remove(Long id) {
        Customer customer = findById(id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }
}
