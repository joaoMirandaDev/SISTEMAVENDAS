/* eslint-disable react-hooks/rules-of-hooks */
import z from 'zod'
import { useTranslate } from '@refinedev/core'
const validaColaborador = () => {
  const t = useTranslate()
  return z.object({
    id: z.optional(z.number()),
    nome: z
      .string()
      .nonempty({ message: t('components.error.requiredField') })
      .toUpperCase(),
    sobrenome: z
      .string()
      .nonempty({ message: t('components.error.requiredField') })
      .toUpperCase(),
    cpf: z
      .string()
      .nonempty({ message: t('components.error.requiredField') })
      .min(11, { message: t('components.error.cpfField') })
      .max(11, { message: t('components.error.cpfField') }),
    rg: z.string().nonempty({ message: t('components.error.requiredField') }),
    dataNascimento: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    dataContratoInicial: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    cep: z
      .string()
      .nonempty({ message: t('components.error.requiredField') })
      .min(8, { message: t('components.error.cepInvalid') })
      .max(8, { message: t('components.error.cepInvalid') }),
    sexo: z.string().nonempty({ message: t('components.error.requiredField') }),
    cidade: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    bairro: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    rua: z.string().nonempty({ message: t('components.error.requiredField') }),
    estado: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    salario: z
      .number()
      .positive({ message: t('components.error.requiredField') }),
    email: z
      .string()
      .nonempty({ message: t('components.error.requiredField') })
      .email()
      .transform(val => val.split('@')[1]),
    senha: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    numero: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    telefone: z
      .string()
      .nonempty({ message: t('components.error.requiredField') }),
    file: z.object({
      name: z.optional(z.string()),
      key: z.optional(z.string()),
    }),
  })
}

export { validaColaborador }
