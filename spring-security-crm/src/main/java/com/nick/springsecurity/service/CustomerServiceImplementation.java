package com.nick.springsecurity.service;

import com.nick.springsecurity.dao.CustomerDao;
import com.nick.springsecurity.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional("sessionFactory")
    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    @Override
    @Transactional("sessionFactory")
    public void saveCustomer(Customer theCustomer) {
        customerDao.saveCustomer(theCustomer);
    }

    @Override
    @Transactional("sessionFactory")
    public Customer getCustomer(int theId) {
        return customerDao.getCustomer(theId);
    }

    @Override
    @Transactional("sessionFactory")
    public void deleteCustomer(int theId) {
        customerDao.deleteCustomer(theId);
    }
}
