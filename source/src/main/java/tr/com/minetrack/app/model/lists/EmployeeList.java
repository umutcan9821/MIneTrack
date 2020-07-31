package tr.com.minetrack.app.model.lists;

import java.util.ArrayList;
import java.util.HashMap;

import tr.com.minetrack.app.db.DAOHelper;
import tr.com.minetrack.app.model.Employee;
import tr.com.minetrack.app.model.Tracked;

public class EmployeeList {
	private final static EmployeeList empListInstance = new EmployeeList();

	private HashMap<Integer, Employee> mapOfEmployees;

	// constructor
	private EmployeeList() {
		mapOfEmployees = DAOHelper.getEmployeeDAO().get(null);
	}

	public static EmployeeList getInstance() {
		return empListInstance;
	}

	public HashMap<Integer, Employee> getList() {
		return mapOfEmployees;
	}

	public boolean add(Employee emp) {
		if (DAOHelper.getEmployeeDAO().insert(emp)) {
			TrackedList.getInstance().add(new Tracked(emp.getFname(), emp.getLname(), emp.getTagId()));
			mapOfEmployees.put(emp.getTagId(), emp);
			return true;
		}
		return false;
	}

	public boolean remove(ArrayList<Integer> tagIdList) {
		if (DAOHelper.getEmployeeDAO().delete(tagIdList)) {
			TrackedList.getInstance().remove(tagIdList);
			for (int key : tagIdList) {
				mapOfEmployees.remove(key);
			}
			return true;
		}
		return false;
	}

	public boolean update(Employee employee) {
		if (DAOHelper.getEmployeeDAO().update(employee, null)) {
			TrackedList.getInstance()
					.update(new Tracked(employee.getFname(), employee.getLname(), employee.getTagId()));
			mapOfEmployees.put(employee.getTagId(), employee);
			return true;
		}
		return false;
	}
}
