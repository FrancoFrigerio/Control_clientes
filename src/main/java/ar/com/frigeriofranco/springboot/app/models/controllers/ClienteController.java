package ar.com.frigeriofranco.springboot.app.models.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import ar.com.frigeriofranco.springboot.app.models.dao.IClienteDao;
import ar.com.frigeriofranco.springboot.app.models.entity.Cliente;
import ar.com.frigeriofranco.springboot.app.models.service.IClienteService;
import ar.com.frigeriofranco.springboot.app.models.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value="/listar")
	public String listar(@RequestParam(name="page",defaultValue = "0") int page,Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 4); //4 registros por pagina
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		 
		PageRender<Cliente> pageRender = new PageRender<>("/cliente/listar", clientes);		model.addAttribute("titulo","Listado de clientes");
		
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
		
		return "listar";
	}
	
	@GetMapping(value="/form")
//	public String crear(Model model) {
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		return "form";
	}
	
	
	@PostMapping(value="/form")
	public String guardar(@Validated Cliente cliente, BindingResult result, Model model , SessionStatus status) {
		if(result.hasErrors()) {
			
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		clienteService.save(cliente);
		status.setComplete();
		return "redirect:/cliente/listar";
	}
	
	
	
	@GetMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Cliente cliente = null;
		if(id > 0 ) {
			cliente = clienteService.findOne(id);
		}else {
			return "redirect:/cliente/listar";
		}
		model.put("titulo", "Formulario de Cliente");
		model.put("cliente", cliente);
		return "form";
		
	}
	@GetMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		if(id > 0) {
			clienteService.delete(id);
		}
		
		return "redirect:/cliente/listar";
	}
	
}
