package rash.testProject.query.sql.getQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import rash.testProject.query.Parameter;

@Data
@AllArgsConstructor
public class ParameterImpl implements Parameter {
    private String name;
    private String value;

}
