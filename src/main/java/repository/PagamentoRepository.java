package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.Pagamento;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public Optional<Pagamento> findById(UUID id){
        return find("id",id).firstResultOptional();
        //return findById(id);
    }


}
