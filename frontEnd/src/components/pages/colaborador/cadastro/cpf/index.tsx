/* eslint-disable @typescript-eslint/no-explicit-any */
import {
  ActionIcon,
  Button,
  Checkbox,
  FileButton,
  Flex,
  Group,
  Image,
  NumberInput,
  PasswordInput,
  Select,
  SelectItem,
  Text,
  TextInput,
  Title,
} from '@mantine/core'
import { DatePickerInput, DateValue, DatesProvider } from '@mantine/dates'
import { useTranslate } from '@refinedev/core'
import 'dayjs/locale/pt-br'
import { useForm, zodResolver } from '@mantine/form'
import api from 'src/utils/Api'
import {
  formatarCPFCNPJ,
  formatarTelefone,
  removeformatacaoTelefone,
  removeformatarCPFCNPJ,
} from 'src/utils/FormatterUtils'
import { useRouter } from 'next/router'
import { ErrorNotification, SuccessNotification } from '@components/common'
import IRole from 'src/interfaces/role'
import { IconCircleXFilled, IconDatabasePlus } from '@tabler/icons-react'
import { IconTrash, IconUpload } from '@tabler/icons'
import { UPLOAD_DOCUMENTOS_TEMP } from 'src/utils/Routes'
import { useRef, useEffect, useState } from 'react'
import { validaColaborador } from '../../validation/schemaColaborador'
interface IFile {
  name: string
  key: string
}
export default function CadastroCpf() {
  const t = useTranslate()
  const [photo, setImagem] = useState<string | null>(null)
  const [checked, setChecked] = useState(false)
  const resetRef = useRef<() => void>(null)
  const [role, setRole] = useState<SelectItem[]>([])
  const navigate = useRouter()
  const form = useForm<{
    id: number | null
    nome: string
    sobrenome: string
    cpf: string
    rg: string
    dataNascimento: string
    dataContratoInicial: string
    cep: string
    sexo: string
    cidade: string
    bairro: string
    rua: string
    estado: string
    isUsuario: null | number
    salario: number
    email: string
    senha: string
    numero: string
    roleId: null | number
    telefone: string
    file: IFile
  }>({
    initialValues: {
      id: null,
      nome: '',
      sobrenome: '',
      cpf: '',
      rg: '',
      dataNascimento: '',
      dataContratoInicial: '',
      cep: '',
      sexo: '',
      cidade: '',
      bairro: '',
      rua: '',
      estado: '',
      isUsuario: null,
      email: '',
      numero: '',
      senha: '',
      roleId: null,
      telefone: '',
      salario: 0,
      file: {
        name: '',
        key: '',
      },
    },
    validate: zodResolver(validaColaborador()),
  })
  const buscarDadosCep = async (value: string) => {
    const dados = await api.get(`/api/endereco/findByRegiao/${value}`)
    console.log(dados)
  }
  const handleCheck = (val: boolean) => {
    setChecked(val)
    handleChange(val === true ? 1 : 0, 'isUsuario')
  }
  const handleChange = (
    event: string | number | boolean | DateValue | File,
    key: string
  ) => {
    form.setFieldValue(key, event)
  }
  const handleSubmit = async () => {
    await api
      .post(`/api/colaborador/adicionar`, form.values)
      .then(response => {
        navigate.push('/colaborador')
        SuccessNotification({ message: response.data })
      })
      .catch(error => {
        ErrorNotification({ message: error })
      })
  }

  const findRole = async () => {
    const dados = await api.get(`/api/role/`)
    const data = dados.data.map((data: IRole) => ({
      value: data.id,
      label: data.name,
    }))
    setRole(data)
  }

  useEffect(() => {
    findRole()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const uploadPhoto = async (file: File) => {
    if (file) {
      const formData = new FormData()
      formData.append('file', file)
      await api
        .post(UPLOAD_DOCUMENTOS_TEMP, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(response => {
          setImagem(URL.createObjectURL(file).toString())
          form.setFieldValue('file.name', file.name)
          form.setFieldValue('file.key', response.data)
        })
        .catch(() => {
          ErrorNotification({ message: t('messages.error') })
        })
    }
  }

  const resetImage = () => {
    setImagem('')
    resetRef.current?.()
    form.setFieldValue('file.name', '')
    form.setFieldValue('file.key', '')
  }

  const renderDadosPessoais = () => (
    <Flex mt={'0.5rem'} direction={'column'}>
      <Flex direction={'column'} align={'center'}>
        <Image
          radius={20}
          width={100}
          height={100}
          src={photo}
          alt=""
          withPlaceholder
          placeholder={<Text align="center">{t('components.photo')}</Text>}
          className="ounded-full"
        />
        <Flex mt={'0.5rem'}>
          <FileButton
            resetRef={resetRef}
            onChange={file => uploadPhoto(file!)}
            accept="image/png,image/jpeg"
          >
            {props => (
              <ActionIcon color="blue" {...props}>
                {<IconUpload />}
              </ActionIcon>
            )}
          </FileButton>
          {photo && (
            <ActionIcon onClick={() => resetImage()} color="red">
              {<IconTrash />}
            </ActionIcon>
          )}
        </Flex>
      </Flex>
      <Text fw={700}>
        {t('pages.colaborador.cadastro.dadosPessoais.title')}
      </Text>
      <Group align={'center'}>
        <TextInput
          {...form.getInputProps('nome')}
          value={form.values.nome}
          withAsterisk
          size="xs"
          required
          w={250}
          onChange={event => handleChange(event.target.value, 'nome')}
          label={t('pages.colaborador.cadastro.dadosPessoais.nome')}
          placeholder={t('pages.colaborador.cadastro.dadosPessoais.inputNome')}
        />

        <TextInput
          withAsterisk
          required
          size="xs"
          defaultValue={form.values?.sobrenome}
          w={250}
          label={t('pages.colaborador.cadastro.dadosPessoais.sobrenome')}
          onChange={event => handleChange(event.target.value, 'sobrenome')}
          placeholder={t(
            'pages.colaborador.cadastro.dadosPessoais.inputSobrenome'
          )}
        />

        <Select
          required
          size={'xs'}
          label={t('pages.colaborador.cadastro.dadosPessoais.sexo')}
          placeholder={t('pages.colaborador.cadastro.dadosPessoais.inputSexo')}
          data={[
            { value: 'Masculino', label: 'Masculino' },
            { value: 'Feminino', label: 'Feminino' },
          ]}
          value="1"
        />

        <TextInput
          withAsterisk
          required
          size="xs"
          defaultValue={form.values?.sexo || ''}
          w={250}
          label={t('pages.colaborador.cadastro.dadosPessoais.sobrenome')}
          onChange={event => handleChange(event.target.value, 'sobrenome')}
          placeholder={t(
            'pages.colaborador.cadastro.dadosPessoais.inputSobrenome'
          )}
        />

        <TextInput
          withAsterisk
          required
          size="xs"
          value={formatarCPFCNPJ(form.values?.cpf || '')}
          w={250}
          onChange={event =>
            handleChange(removeformatarCPFCNPJ(event.target.value), 'cpf')
          }
          label={t('pages.colaborador.cadastro.dadosPessoais.cpf')}
          placeholder={t('pages.colaborador.cadastro.dadosPessoais.inputCpf')}
        />

        <TextInput
          withAsterisk
          w={250}
          required
          size="xs"
          onChange={event => handleChange(event.target.value, 'rg')}
          defaultValue={form.values?.rg || ''}
          label={t('pages.colaborador.cadastro.dadosPessoais.rg')}
          placeholder={t('pages.colaborador.cadastro.dadosPessoais.inputRg')}
        />
        <DatesProvider
          settings={{
            locale: 'pt-br',
          }}
        >
          <DatePickerInput
            // value={data.dataInicial}
            withAsterisk={false}
            size="xs"
            required
            clearable
            onChange={val => handleChange(val, 'dataInicial')}
            label={t(
              'pages.colaborador.cadastro.dadosPessoais.dataContratacao'
            )}
            placeholder={t(
              'pages.colaborador.cadastro.dadosPessoais.inputDataContratacao'
            )}
            maxDate={new Date()}
            w={'15.625rem'}
          />
          <DatePickerInput
            // value={data.dataFinal}
            onChange={val => handleChange(val, 'dataFinal')}
            withAsterisk={false}
            clearable
            required
            w={'15.625rem'}
            size="xs"
            label={t('pages.colaborador.cadastro.dadosPessoais.dataNascimento')}
            placeholder={t(
              'pages.colaborador.cadastro.dadosPessoais.inputDataNascimento'
            )}
            maxDate={new Date()}
          />
        </DatesProvider>
      </Group>
    </Flex>
  )

  const renderDadosEndereco = () => (
    <>
      <Title fw={700} mt={20} size="md">
        {t('pages.colaborador.cadastro.endereco.title')}
      </Title>
      <Group align={'center'}>
        <TextInput
          required
          withAsterisk
          size="xs"
          w={250}
          defaultValue={form.values?.cep || ''}
          onBlur={e => {
            buscarDadosCep(e.target.value || '')
          }}
          label={t('pages.colaborador.cadastro.endereco.endereco.cep')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputCep'
          )}
        />

        <TextInput
          withAsterisk
          required
          size="xs"
          w={250}
          defaultValue={form.values?.cidade || ''}
          onChange={event => handleChange(event.target.value, 'cidade')}
          label={t('pages.colaborador.cadastro.endereco.endereco.cidade')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputCidade'
          )}
        />

        <TextInput
          withAsterisk
          required
          w={250}
          size="xs"
          defaultValue={form.values?.estado || ''}
          onChange={event => handleChange(event.target.value, 'estado')}
          label={t('pages.colaborador.cadastro.endereco.endereco.estado')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputEstado'
          )}
        />

        <TextInput
          withAsterisk
          w={250}
          required
          size="xs"
          defaultValue={form.values?.bairro || ''}
          onChange={event => handleChange(event.target.value, 'bairro')}
          label={t('pages.colaborador.cadastro.endereco.endereco.bairro')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputBairro'
          )}
        />

        <TextInput
          withAsterisk
          size="xs"
          w={250}
          required
          onChange={event => handleChange(event.target.value, 'rua')}
          defaultValue={form.values?.rua || ''}
          label={t('pages.colaborador.cadastro.endereco.endereco.rua')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputRua'
          )}
        />

        <TextInput
          withAsterisk
          required
          w={250}
          size="xs"
          defaultValue={form.values?.numero || ''}
          onChange={event => handleChange(event.target.value, 'numero')}
          label={t('pages.colaborador.cadastro.endereco.endereco.numero')}
          placeholder={t(
            'pages.colaborador.cadastro.endereco.endereco.inputNumero'
          )}
        />
      </Group>
    </>
  )

  const renderContatos = () => (
    <>
      <Text fw={700} mt={20} size="md">
        {t('pages.colaborador.cadastro.contatos.title')}
      </Text>
      <Group>
        <TextInput
          withAsterisk
          w={250}
          size="xs"
          value={formatarTelefone(form.values?.telefone || '') || ''}
          onChange={event =>
            handleChange(
              removeformatacaoTelefone(event.target.value),
              'telefone'
            )
          }
          label={t('pages.colaborador.cadastro.contatos.contato.telefone')}
          placeholder={t(
            'pages.colaborador.cadastro.contatos.contato.inputTelefone'
          )}
        />
        <TextInput
          withAsterisk
          w={250}
          size="xs"
          value={formatarTelefone(form.values?.email || '') || ''}
          onChange={event =>
            handleChange(
              removeformatacaoTelefone(event.target.value),
              'telefone'
            )
          }
          label={t('pages.colaborador.cadastro.contatos.contato.email')}
          placeholder={t(
            'pages.colaborador.cadastro.contatos.contato.inputTelefone'
          )}
        />
      </Group>
    </>
  )

  const renderDadosAdministrativos = () => (
    <>
      <Text fw={700} mt={'0.5rem'} size="md">
        {t('pages.colaborador.cadastro.administrativo.salario')}
      </Text>
      <Group>
        <NumberInput
          withAsterisk
          hideControls
          min={0}
          w={250}
          size="xs"
          precision={2}
          decimalSeparator=","
          thousandsSeparator="."
          value={form.values?.salario}
          onChange={event => handleChange(event, 'salario')}
          label={t('pages.colaborador.cadastro.administrativo.salario')}
          placeholder={t(
            'pages.colaborador.cadastro.administrativo.inputSalario'
          )}
        />
      </Group>
    </>
  )

  const renderUsuario = () => (
    <>
      <Checkbox
        checked={checked}
        mt={'0.5rem'}
        onChange={event => handleCheck(event.currentTarget.checked)}
        label={t('pages.colaborador.cadastro.usuario.check')}
      />
      {checked && (
        <>
          <Text fw={700} mt={'0.5rem'} size="md">
            {t('pages.colaborador.cadastro.usuario.title')}
          </Text>
          <Group>
            <PasswordInput
              withAsterisk
              w={250}
              size="xs"
              value={form.values?.senha}
              onChange={event => handleChange(event.target.value, 'senha')}
              label={t('pages.colaborador.cadastro.usuario.senha')}
              placeholder={t('pages.colaborador.cadastro.usuario.inputSenha')}
            />

            <Select
              withAsterisk
              w={220}
              label={t('pages.colaborador.cadastro.usuario.perfil')}
              placeholder={t('pages.colaborador.cadastro.usuario.inputPerfil')}
              onChange={event => handleChange(event, 'role')}
              withinPortal
              size="xs"
              data={role}
            />
          </Group>
        </>
      )}
    </>
  )

  const renderButtons = () => (
    <>
      <Flex mt={20} bottom={0} pos={'relative'} justify={'space-between'}>
        <Button
          leftIcon={<IconCircleXFilled />}
          color="red"
          onClick={() => navigate.push('/colaborador')}
        >
          {t('components.button.cancelar')}
        </Button>
        <Button leftIcon={<IconDatabasePlus />} type="submit" color="green">
          {t('components.button.salvar')}
        </Button>
      </Flex>
    </>
  )

  return (
    <form onSubmit={form.onSubmit(handleSubmit)}>
      {renderDadosPessoais()}
      {renderDadosEndereco()}
      {renderContatos()}
      {renderDadosAdministrativos()}
      {renderUsuario()}
      {renderButtons()}
    </form>
  )
}
