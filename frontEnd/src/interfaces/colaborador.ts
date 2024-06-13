export default interface IColaborador {
  salario: number
  dataContratoFinal: string
  dataContratoInicial: string
  dataNascimento: string
  ativo: string | number
  id?: number
  sobrenome?: string
  nome?: string
  numero?: string
  cpf?: string
  rg?: string
  cep?: string
  rua?: string
  bairro?: string
  cidade?: string
  isUsuario?: number
  estado?: string
  telefone?: string
  email?: string
  senha?: string
  role?: number
}
