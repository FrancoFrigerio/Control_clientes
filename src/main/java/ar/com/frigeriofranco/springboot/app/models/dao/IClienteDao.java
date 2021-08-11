package ar.com.frigeriofranco.springboot.app.models.dao;
import org.springframework.data.repository.PagingAndSortingRepository;

import ar.com.frigeriofranco.springboot.app.models.entity.Cliente;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

	
}
