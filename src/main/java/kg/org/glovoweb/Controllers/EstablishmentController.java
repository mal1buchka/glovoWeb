package kg.org.glovoweb.Controllers;

import kg.org.glovoweb.Models.Establishment;
import kg.org.glovoweb.Repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/non-secured/establishments")
public class EstablishmentController {
    @Autowired
    private EstablishmentRepository establishmentRepository;

    @GetMapping
    public List<Establishment> getAllEstablishments() {
        return establishmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Establishment> getEstablishmentById(@PathVariable String id) {
        return establishmentRepository.findById(id);
    }
}
