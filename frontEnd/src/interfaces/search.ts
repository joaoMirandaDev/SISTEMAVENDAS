export default interface ISearch {
  search?: string | Date | string
  id?: string
  desc?: boolean
  pagina: number
  tamanhoPagina: number
  nome?: string
  sobrenome?: string
  cpf?: string
  estado?: string
  cidade?: string
  ativo?: number | string
}
