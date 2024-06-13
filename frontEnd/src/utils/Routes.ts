/* Rotas caixa */
export const CAIXA_PAGE: string = '/api/caixa/list'
export const CAIXA_OPEN: string = '/api/caixa/openCaixa'
export const CAIXA_CLOSE: string = '/api/caixa/close/'
export const CAIXA_BY_ID: string = '/api/caixa/findById/'
export const CAIXA_GET_VALUES_BY_DASHBOARD_MONTH: string =
  '/api/caixa/getValuesCaixaByGroupByMes'
export const CAIXA_GET_VALUES_BY_DASHBOARD: string =
  '/api/caixa/getValuesCaixaByDashBoard'

/* Rotas compras */
export const COMPRAS_PAGE: string = '/api/compras/list'
export const COMPRAS_BY_ID: string = 'api/compras'
export const COMPRAS_ADD: string = 'api/compras/addCompra'
export const COMPRAS_EDIT_BY_ID: string = 'api/compras/edit'
export const COMPRAS_DELETE_BY_ID: string = '/api/compras/deleteById/'
export const COMPRAS_GET_VALOR_TOTAL: string = '/api/compras/getValorCompras'

/* Rotas categoria */
export const FIND_ALL_CATEGORIA: string = 'api/categoria/findAll'

/* Rotas colaborador */
export const FIND_COLABORADOR: string = '/api/colaborador/findByCpfCnpj/'

/* Rotas fornecedores */
export const FIND_ALL_FORNECEDOR: string = 'api/fornecedor/findAll'

/* Rotas forma pagamento */
export const FIND_ALL_FORMA_PAGAMENTO: string = 'api/formaPagamento/findAll'

/* Rotas mecadoria */
export const MERCADORIA_PAGE: string = '/api/mercadoria/list'
export const MERCADORIA_ADD: string = 'api/mercadoria/adicionar'
export const FIND_ALL_MERCADORIA: string = 'api/mercadoria/findAll'
export const MERCADORIA_BY_ID: string = 'api/mercadoria/findById/'
export const MERCADORIA_DELETE_BY_ID: string = '/api/mercadoria/deleteById/'
export const MERCADORIA_EDITAR_BY_ID: string = 'api/mercadoria/editar'

/* Rotas mercadoria comprs */
export const MERCADORIA_COMPRAS_BY_ID_PAGE: string = 'api/merdoriasCompra/list/'

/* Rotas produtos */
export const PRODUTO_PAGE: string = '/api/especialidade/list'
export const PRODUTO_BY_ID: string = 'api/especialidade/findById/'
export const PRODUTO_ADD: string = 'api/especialidade/adicionar'
export const PRODUTO_DELETE_BY_ID: string = '/api/especialidade/delete/'
export const PRODUTO_EDIT: string = 'api/especialidade/editar'
export const FIND_ALL_ESPECIALIDADE: string = 'api/especialidade/findAll'

/* Rotas pedido */
export const PEDIDO_PAGE: string = '/api/pedido/list/'
export const DELETE_BY_ID: string = '/api/pedido/delete/'
export const EDITPEDIDO_BY_ID: string = '/api/pedido/editPedido'
export const PEDIDO_BY_ID: string = '/api/pedido/findDtoById/'
export const PEDIDO_BY_ID_COMPLETO: string = '/api/pedido/findById/'
export const PEDIDO_PAYMENT: string = '/api/pedido/payment/'
export const PEDIDO_ADD: string = '/api/pedido/addPedido/'
export const GET_VALOR_TOTAL_BY_CAIXA: string = '/api/pedido/getValorTotal/'
export const GET_VALOR_TOTAL_BY_FORMA_PAGAMENTO: string =
  '/api/pedido/getValorTotalVendasByFormaPagamento'
export const GET_VALOR_TOTAL_VENDAS: string = '/api/pedido/getValorTotalVendas'
export const GET_VALOR_TOTAL_PEDIDOS: string =
  '/api/pedido/getQuantidadePedidos'

/* Rotas pedido especialidade */
export const GET_TOP_PEDIDOS_ESPECIALIDADE: string =
  '/api/pedidoEspecialidade/getTopPedidosEspecialidade'

/* Rotas unidade medida */
export const FIND_ALL_UNIDADE_MEDIDA: string = 'api/unidadeMedida/findAll'

/* Rotas tipo */
export const FIND_ALL_TIPO: string = 'api/tipo'

/* Rotas tipoPedido */
export const FIND_ALL_TIPO_PEDIDO: string = 'api/tipoPedido'

/* Rotas usuario */
export const FIND_BY_USUARIO_LOGIN: string = '/api/usuarios/findByLogin/'
export const VALIDATOR_USUARIO: string = '/api/usuarios/validatorUser/'
export const AUTH_USUARIO: string = '/api/usuarios/auth'
