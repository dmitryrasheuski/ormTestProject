package rash.testProject.query;

import java.util.List;

public interface Context {
    void add(Object key, Object value);
    String getExecutableString();
    List<Parameter> getParameters();
}
