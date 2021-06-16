package entities.projecthours;

import javax.ws.rs.WebApplicationException;

public interface ProjectUserHoursRepository {

    void editProjectUserHours(String username, Integer id, Integer hoursWorked) throws WebApplicationException;

}
