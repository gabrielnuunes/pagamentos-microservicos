package com.ganoo.pagamentos.service;

import com.ganoo.pagamentos.dto.PagamentoDto;
import com.ganoo.pagamentos.model.Pagamento;
import com.ganoo.pagamentos.model.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ganoo.pagamentos.repository.PagamentoRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<PagamentoDto> listar(Pageable paginacao) {
		return pagamentoRepository
				.findAll(paginacao)
				.map(p -> modelMapper.map(p, PagamentoDto.class));
	}

	public PagamentoDto buscar(Long id) {
		Pagamento pagamento = pagamentoRepository
				.findById(id)
//				.orElseThrow(() -> new EntityNotFoundException());
				.orElseThrow(EntityNotFoundException::new);

		return modelMapper.map(pagamento, PagamentoDto.class);
	}

	public PagamentoDto criar(PagamentoDto dto) {
		Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
		pagamento.setStatus(Status.CRIADO);
		pagamentoRepository.save(pagamento);

		return modelMapper.map(pagamento, PagamentoDto.class);
	}

}
