package sssvn.personnel.validators;

import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import sssvn.personnel.Employment;
import sssvn.personnel.EmploymentCo;
import sssvn.personnel.Person;
import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.OrderingModel;
import ua.com.fielden.platform.error.Result;

public class EmploymentFinishDateValidator extends AbstractBeforeChangeEventHandler<Date> {
	
	@Override
	public Result handle(final MetaProperty<Date> property, final Date finishDate, final Set<Annotation> mutatorAnnotations) {
		
		final Employment employment = property.getEntity();
		final Person employee = employment.getEmployee();
		
		if (employee == null || finishDate == null) {
			return Result.successful(finishDate);
		}
		
		final EntityResultQueryModel<Employment> query = select(Employment.class)
				.where().prop("employee").eq().val(employee.getId())
				.model();
		final fetch<Employment> fetch = EmploymentCo.FETCH_PROVIDER.fetchModel();
		final OrderingModel orderBy = orderBy().prop("startDate").asc().model();
		final QueryExecutionModel<Employment, EntityResultQueryModel<Employment>> qem = from(query).with(fetch).with(orderBy)
				.model();
		
		List<Employment> employmentList = co(Employment.class).getAllEntities(qem);

		employmentList.forEach(emp -> System.out.printf("Employment: %s, Person: %s, Contract no: %s\n", emp, emp.getEmployee(), emp.getContractNo()));
		
		for (int i = 0; i < employmentList.size(); i++) {
			final Employment emp = employmentList.get(i);
			
			if (emp.compareTo(employment) == 0) {
				continue;
			}
			
			final Date currStartDate = emp.getStartDate();
			final Date currFinishDate = emp.getFinishDate();
			
			if (finishDate.compareTo(currStartDate) >= 0 && (currFinishDate == null || finishDate.compareTo(currFinishDate) <= 0)) {
				return Result.failure(EmploymentStartDateValidator.ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION);
			}
			
			final Date startDate = employment.getStartDate();
			
			if (startDate != null && startDate.compareTo(currStartDate) <= 0 && finishDate.compareTo(currFinishDate) >= 0) {
				return Result.failure(EmploymentStartDateValidator.ERR_EMPLOYEE_CURR_EMPLOYMENT_INTERSECTION);
			}
		}
				
		
		return Result.successful(finishDate);
	}

}
