package lk.ijse.royal_care_pharmacy.service.util;

import lk.ijse.royal_care_pharmacy.dto.*;
import lk.ijse.royal_care_pharmacy.entity.*;

public class Convertor {

    public AttendenceDTO fromAttendence(Attendence attendence) {
        return new AttendenceDTO(attendence.getNIC(), attendence.getEID(), attendence.getEName(), attendence.getADate(), attendence.getDescription());
    }

    public Attendence toAttendence (AttendenceDTO attendenceDTO){
        return new Attendence(attendenceDTO.getNIC(), attendenceDTO.getEID(), attendenceDTO.getEName(), attendenceDTO.getADate(), attendenceDTO.getDescription());
    }

    public BatchDTO fromBatch(Batch batch){
        return new BatchDTO(batch.getBatchId(), batch.getBName(), batch.getDescription(), batch.getUnitPrice(), batch.getQTY(), batch.getMdfExpDate());

    }
    public Batch toBatch (BatchDTO batchDTO){
        return new Batch(batchDTO.getBatchId(),batchDTO.getBName(), batchDTO.getDescription(), batchDTO.getUnitPrice(), batchDTO.getQTY(), batchDTO.getMdfExpDate());
    }

    public CustomerDTO fromCustomer(Customer customer){
        return new CustomerDTO(customer.getCusId(), customer.getCusName(), customer.getCusAge(), customer.getCusAddress(), customer.getCusTelNumber());
    }

    public Customer toCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getCusId(), customerDTO.getCusName(), customerDTO.getCusAge(), customerDTO.getCusAddress(), customerDTO.getCusTelNumber());
    }

    public EmployeeDTO fromEmployee(Employee employee){
        return new EmployeeDTO(employee.getEId(), employee.getEName(), employee.getEAddress(), employee.getEmail(), employee.getETelNumber(), employee.getEPassword(), employee.getUser_Name());
    }

    public Employee toEmployee(EmployeeDTO employeeDTO){
        return new Employee(employeeDTO.getEId(), employeeDTO.getEName(), employeeDTO.getEAddress(), employeeDTO.getEmail(), employeeDTO.getETelNumber(), employeeDTO.getEPassword(), employeeDTO.getUser_Name());
    }

    public ItemDTO fromItem(Item item){
        return new ItemDTO(item.getItemId(), item.getItem_Name(), item.getPrice(), item.getMadeOfDate(), item.getExpireOfDate(), item.getQTY(), item.getDescription());
    }

    public Item toItem(ItemDTO itemDTO){
        return new Item(itemDTO.getItemId(), itemDTO.getItem_Name(), itemDTO.getPrice(), itemDTO.getMadeOfDate(), itemDTO.getExpireOfDate(), itemDTO.getQTY(), itemDTO.getDescription());
    }

   /* public OrderDetailDTO fromOrderDetail (OrderDetail orderDetail){
        return new OrderDetailDTO(orderDetail.getOID(), orderDetail.getItemId(), orderDetail.getPrice(), orderDetail.getQty());
    }

    public OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO){
        return new OrderDetail(orderDetailDTO.getOID(), orderDetailDTO.getItemId(), orderDetailDTO.getPrice(),orderDetailDTO.getQty());
    }*/

    public OrderDTO fromOrder(Order order){
        return new OrderDTO(order.getOID(), order.getCusId(), order.getODate());
    }

    public Order toOrder(OrderDTO orderDTO){
        return new Order(orderDTO.getOID(),orderDTO.getCusId(),orderDTO.getODate());
    }

    public SalaryDTO fromSalary(Salary salary){
        return new SalaryDTO(salary.getEID(), salary.getEName(),salary.getSalId(),salary.getAmount(),salary.getPMonth(),salary.getPaidOrNot());
    }

    public Salary toSalary(SalaryDTO salaryDTO){
        return new Salary(salaryDTO.getEID(), salaryDTO.getEName(),salaryDTO.getSalId(),salaryDTO.getAmount(),salaryDTO.getPMonth(),salaryDTO.getPaidOrNot());
    }

    public SupplierDTO fromSupplier(Supplier supplier){
        return new SupplierDTO(supplier.getSupId(), supplier.getSName(), supplier.getAddress(), supplier.getEmail(),supplier.getTelNumber());
    }

    public Supplier toSupplier(SupplierDTO supplierDTO){
        return new Supplier(supplierDTO.getSupId(), supplierDTO.getSName(), supplierDTO.getAddress(), supplierDTO.getEmail(), supplierDTO.getTelNumber());
    }

}
