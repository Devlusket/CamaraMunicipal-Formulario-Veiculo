package com.camara.veiculo.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camara.veiculo.dto.response.FormularioResponseDTO;
import com.camara.veiculo.dto.response.VeiculoResponseDTO;
import com.camara.veiculo.exception.ResourceNotFoundException;
import com.camara.veiculo.repository.VeiculoRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final FormularioService formularioService;
    private final VeiculoRepository veiculoRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] gerarRelatorio(Long veiculoId, int ano, Integer mes) {

        // 1. Busca o veículo
        VeiculoResponseDTO veiculo = veiculoRepository.findById(veiculoId)
            .map(VeiculoResponseDTO::fromEntity)
            .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id: " + veiculoId));

        // 2. Busca os formulários do período
        List<FormularioResponseDTO> formularios = formularioService.buscarPorPeriodo(veiculoId, ano, mes);

        // 3. Monta o período para exibição
        String periodo = mes != null
            ? String.format("%02d/%d", mes, ano)
            : String.valueOf(ano);

        // 4. Gera o PDF em memória
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // --- CABEÇALHO ---
            document.add(new Paragraph("Câmara Municipal")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Relatório de Uso de Veículos")
                .setFontSize(13)
                .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Veículo: " + veiculo.nome() + " — Placa: " + veiculo.placa())
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Período: " + periodo)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

            // --- TABELA ---
            float[] colunas = {2, 2, 2, 1.5f, 1.5f, 1, 2};
            Table tabela = new Table(UnitValue.createPercentArray(colunas))
                .useAllAvailableWidth();

            // Cabeçalho da tabela
            String[] cabecalhos = {
                "Saída", "Retorno Previsto", "Requisitante",
                "Cargo", "Destino/Itinerário", "Odômetro", "Observação"
            };
            for (String cab : cabecalhos) {
                tabela.addHeaderCell(new Cell()
                    .add(new Paragraph(cab).setBold())
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER));
            }

            // Linhas da tabela
            for (FormularioResponseDTO f : formularios) {
                tabela.addCell(formatarData(f.dataSaida()));
                tabela.addCell(formatarData(f.dataRetornoPrevista()));
                tabela.addCell(f.requisitante());
                tabela.addCell(f.cargo());
                tabela.addCell(f.itinerario());
                tabela.addCell(String.valueOf(f.odometroSaida()));
                tabela.addCell(f.observacao() != null ? f.observacao() : "—");
            }

            document.add(tabela);

            // --- RODAPÉ ---
            document.add(new Paragraph("\nTotal de registros no período: " + formularios.size())
                .setFontSize(10)
                .setMarginTop(15));

            document.add(new Paragraph("Relatório gerado em: " + LocalDateTime.now().format(FORMATTER))
                .setFontSize(10));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF: " + e.getMessage(), e);
        }

        return outputStream.toByteArray();
    }

    private String formatarData(LocalDateTime data) {
        return data != null ? data.format(FORMATTER) : "—";
    }
}