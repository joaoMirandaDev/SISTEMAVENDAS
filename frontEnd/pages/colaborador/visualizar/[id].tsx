import { Button, Card, Divider, Flex, Group, Text, Title } from '@mantine/core'
import { useTranslate } from '@refinedev/core'
import { IconArrowBarLeft } from '@tabler/icons'
import { GetServerSideProps } from 'next'
import { serverSideTranslations } from 'next-i18next/serverSideTranslations'
import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'
import IColaborador from 'src/interfaces/colaborador'
import api from 'src/utils/Api'
import { formatarCPFCNPJ, formatarTelefone } from 'src/utils/FormatterUtils'

export default function ShowFornecedor() {
  const t = useTranslate()
  const [dataCliente, setDataCliente] = useState<IColaborador>()
  const navigate = useRouter()
  const router = useRouter()
  const { id } = router.query
  const [, setTitle] = useState<string>('')
  const findByColaboradorId = async () => {
    const value = await api.get(`/api/colaborador/findById/${id}`)
    setDataCliente(value.data)
  }
  useEffect(() => {
    findByColaboradorId()
    if (dataCliente?.cpf && dataCliente?.cpf?.length == 11) {
      setTitle(t('pages.fornecedor.cadastro.cpf.title.dadosPessoais'))
    } else {
      setTitle(t('pages.fornecedor.cadastro.cpf.title.dadosEmpresariais'))
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const renderDadosPessoais = () => (
    <>
      <Title fw={700} size="md" mb={20}>
        {t('pages.fornecedor.cadastro.cpf.title.dadosPessoais')}
      </Title>
      <Flex
        wrap={'wrap'}
        justify="flex-start"
        align="flex-start"
        gap="md"
        direction="column"
      >
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputName')} :
          </Text>
          {dataCliente?.nome}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputSobrenome')} :
          </Text>
          {dataCliente?.sobrenome}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputCpf')} :
          </Text>
          {formatarCPFCNPJ(dataCliente?.cpf || '')}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputRg')} :
          </Text>
          {dataCliente?.rg}
        </Group>
      </Flex>
    </>
  )
  const renderDadosEndereco = () => (
    <>
      <Title fw={700} mt={20} size="md" mb={20}>
        {t('pages.fornecedor.cadastro.cpf.title.endereco')}
      </Title>
      <Flex
        wrap={'wrap'}
        justify="flex-start"
        align="flex-start"
        gap="md"
        direction="column"
      >
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputCep')} :
          </Text>
          {dataCliente?.cep}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputCidade')} :{' '}
          </Text>
          {dataCliente?.cidade}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputBairro')} :{' '}
          </Text>
          {dataCliente?.bairro}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputRua')} :
          </Text>
          {dataCliente?.rua}
        </Group>
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputNumero')} :{' '}
          </Text>
          {dataCliente?.numero}
        </Group>
      </Flex>
    </>
  )

  const renderContatos = () => (
    <>
      <Title fw={700} mt={20} size="md" mb={20}>
        {t('pages.fornecedor.cadastro.cpf.title.contato')}
      </Title>
      <Flex
        wrap={'wrap'}
        justify="flex-start"
        align="flex-start"
        gap="md"
        direction="column"
      >
        <Group mr={20}>
          <Text fw={'bold'}>
            {t('pages.fornecedor.cadastro.cpf.inputTelefone')} :{' '}
          </Text>
          {formatarTelefone(dataCliente?.telefone || '')}
        </Group>
      </Flex>
    </>
  )
  const renderButtons = () => (
    <>
      <Flex mt={20} justify={'flex-start'}>
        <Button
          leftIcon={<IconArrowBarLeft />}
          onClick={() => navigate.push('/colaborador')}
        >
          {t('components.button.voltar')}
        </Button>
      </Flex>
    </>
  )
  return (
    <>
      <Card>
        {renderDadosPessoais()}
        <Divider />
        {renderDadosEndereco()}
        <Divider />
        {renderContatos()}
      </Card>
      {renderButtons()}
    </>
  )
}
export const getServerSideProps: GetServerSideProps = async context => {
  const translateProps = await serverSideTranslations(context.locale ?? 'pt', [
    'common',
  ])

  return {
    props: {
      ...translateProps,
    },
  }
}
