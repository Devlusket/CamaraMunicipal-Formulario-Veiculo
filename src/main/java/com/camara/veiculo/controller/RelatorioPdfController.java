package com.camara.veiculo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camara.veiculo.service.PdfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Relatórios", description = "Geração de relatórios PDF — admin")
public class RelatorioPdfController {

    private final PdfService pdfService;

    @GetMapping("/relatorios/pdf")
    @Operation(summary = "Gera relatório PDF de uso do veículo — admin")
    public ResponseEntity<byte[]> gerarRelatorio(
            @RequestParam Long veiculoId,
            @RequestParam int ano,
            @RequestParam(required = false) Integer mes) {

        byte[] pdf = pdfService.gerarRelatorio(veiculoId, ano, mes);

        String nomeArquivo = mes != null
            ? String.format("relatorio-%02d-%d.pdf", mes, ano)
            : String.format("relatorio-%d.pdf", ano);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", nomeArquivo);

        return ResponseEntity.ok()
            .headers(headers)
            .body(pdf);
    }
}