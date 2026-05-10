# Architecture

Flow:

Controller -> Service -> DAO -> Database

---

# Rules

- Controller: handle request/response only
- Service: handle business logic
- DAO: handle database operations
- JSP: render UI only

Forbidden:

- No SQL in Controller
- No business logic in JSP
- No DAO access directly from Controller

---

# GenericDAO

```java
public abstract class GenericDAO<T> {

    public void create(T entity) {}

    public void update(T entity) {}

    public void delete(T entity) {}

    public T findById(Serializable id) {}

    public List<T> findAll() {}
}
```
