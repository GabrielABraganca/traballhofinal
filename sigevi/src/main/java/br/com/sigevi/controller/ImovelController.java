package br.com.sigevi.controller;

import br.com.sigevi.dto.request.ImovelRequest;
import br.com.sigevi.dto.response.ImovelResponse;
import br.com.sigevi.security.SecurityUtils;
import br.com.sigevi.service.ImovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller responsável por gerenciar as operações relacionadas aos imóveis.
 * Disponibiliza endpoints para cadastro, consulta e atualização de imóveis.
 */
@RestController
@RequestMapping("/imoveis")
@Tag(name = "Imoveis")
@SecurityRequirement(name = "bearerAuth")
public class ImovelController {

    private final ImovelService imovelService;
    private final SecurityUtils securityUtils;

    public ImovelController(ImovelService imovelService, SecurityUtils securityUtils) {
        this.imovelService = imovelService;
        this.securityUtils = securityUtils;
    }
/**
     * Realiza o cadastro de um novo imóvel vinculado ao usuário autenticado.
     *
     * @param request Dados do imóvel a ser cadastrado.
     * @return Dados do imóvel cadastrado.
     */
    @PostMapping
    @Operation(summary = "Cadastrar imovel")
    public ResponseEntity<ImovelResponse> cadastrar(@Valid @RequestBody ImovelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(imovelService.cadastrar(request, securityUtils.getUsuarioLogadoId()));
    }
 /**
     * Busca um imóvel pelo seu identificador único.
     *
     * @param id ID do imóvel.
     * @return Dados do imóvel encontrado.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar imovel por ID")
    public ResponseEntity<ImovelResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(imovelService.buscarPorId(id));
    }
 /**
     * Atualiza os dados de um imóvel existente.
     *
     * @param id ID do imóvel a ser atualizado.
     * @param request Novos dados do imóvel.
     * @return Dados atualizados do imóvel.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar imovel")
    public ResponseEntity<ImovelResponse> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody ImovelRequest request) {
        return ResponseEntity.ok(imovelService.atualizar(id, request, securityUtils.getUsuarioLogadoId()));
    }
/**
     * Busca um imóvel através do número de matrícula.
     *
     * @param matricula Matrícula do imóvel.
     * @return Dados do imóvel encontrado.
     */
    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Buscar imovel por matricula")
    public ResponseEntity<ImovelResponse> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(imovelService.buscarPorMatricula(matricula));
    }
  /**
     * Busca imóveis com base em um trecho do endereço informado.
     *
     * @param q Texto utilizado na pesquisa do endereço.
     * @return Lista de imóveis encontrados.
     */
    @GetMapping("/endereco")
    @Operation(summary = "Buscar imoveis por endereco")
    public ResponseEntity<List<ImovelResponse>> buscarPorEndereco(@RequestParam String q) {
        return ResponseEntity.ok(imovelService.buscarPorEndereco(q));
    }
}
