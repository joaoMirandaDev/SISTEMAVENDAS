import { ErrorNotification, SuccessNotification } from '@components/common'
import PaginationTable from '@components/common/tabela/paginationTable'
import ModalColaborador from '@components/pages/colaborador/modal'
import {
  ActionIcon,
  Box,
  Button,
  Divider,
  Flex,
  Group,
  Image,
  Text,
  Tooltip,
} from '@mantine/core'
import { useTranslate } from '@refinedev/core'
import { IconEdit, IconTrash, IconUserPlus } from '@tabler/icons'
import Cookies from 'js-cookie'
import {
  MRT_ColumnDef,
  MRT_PaginationState,
  MRT_Row,
  MRT_SortingState,
} from 'mantine-react-table'
import { GetServerSideProps } from 'next'
import { serverSideTranslations } from 'next-i18next/serverSideTranslations'
import { useRouter } from 'next/router'
import { useEffect, useMemo, useState } from 'react'
import IColaborador from 'src/interfaces/colaborador'
import ISearch from 'src/interfaces/search'
import api from 'src/utils/Api'
import { PAGE_INDEX, PAGE_SIZE } from 'src/utils/Constants'
import {
  formataCep,
  formatarCPFCNPJ,
  formatarTelefone,
} from 'src/utils/FormatterUtils'

interface IColaboradorProps {
  id: string
  value: string
}

export default function ColaboradorList() {
  const t = useTranslate()
  const navigate = useRouter()
  const [openModal, setOpenModal] = useState<boolean>(false)
  const [columnFilters, setColumnFilters] = useState<IColaboradorProps[]>([])
  const [idCliente, setIdCliente] = useState<number>(0)
  const [sorting, setSorting] = useState<MRT_SortingState>([])
  const [dataCliente, setDataCliente] = useState<IColaborador[]>([])
  const [totalElements, setTotalElements] = useState<number>(0)
  const [pagination, setPagination] = useState<MRT_PaginationState>({
    pageIndex: PAGE_INDEX,
    pageSize: PAGE_SIZE,
  })
  const [filtro, setFiltro] = useState<ISearch>({
    nome: '',
    sobrenome: '',
    cpf: '',
    estado: '',
    cidade: '',
    ativo: 0,
    pagina: 0,
    tamanhoPagina: 10,
    id: 'nome',
    desc: false,
  })
  const resetForm = () => {
    const val = {
      nome: '',
      sobrenome: '',
      cpf: '',
      estado: '',
      cidade: '',
      ativo: 0,
      pagina: 0,
      tamanhoPagina: 10,
      id: 'nome',
      desc: false,
    }
    setFiltro(val)
  }
  useEffect(() => {
    if (
      pagination.pageIndex !== filtro.pagina ||
      pagination.pageSize !== filtro.tamanhoPagina
    ) {
      const localFiltro = {
        ...filtro,
        tamanhoPagina: pagination.pageSize,
        pagina: pagination.pageIndex,
      }
      setFiltro(localFiltro)
    }
    findAllColaborador()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pagination, filtro])

  useEffect(() => {
    if (columnFilters.length > 0) {
      columnFilters.forEach(column => {
        switch (column.id) {
          case 'nome':
            filterCliente('nome', column.value)
            break
          case 'sobrenome':
            filterCliente('sobrenome', column.value)
            break
          case 'cidade':
            filterCliente('cidade', column.value)
            break
          case 'cpf':
            filterCliente('cpf', column.value.replace(/[. -]/g, ''))
            break
          case 'estado':
            filterCliente('estado', column.value)
            break
          case 'ativo':
            {
              const val = column.value === 'Ativo' ? 0 : 1
              filterCliente('ativo', val)
            }
            break
          default:
            break
        }
      })
    } else {
      resetForm()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [columnFilters])

  useEffect(() => {
    if (sorting.length == 0) {
      setSorting([{ id: 'nome', desc: false }])
    }
    sorting.map(value => {
      setFiltro(prevData => ({ ...prevData, id: value.id, desc: value.desc }))
    })
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [sorting])

  const filterCliente = (Key: string, value: string | number) => {
    setFiltro(prevData => ({ ...prevData, [Key]: value, pagina: 0 }))
  }

  const findAllColaborador = async () => {
    const value = await api.post('/api/colaborador/list', filtro)
    value.data.content.map((value: { cpf: string; telefone: string }) => {
      value.cpf = formatarCPFCNPJ(value.cpf)
      value.telefone = formatarTelefone(value.telefone)
    })
    setDataCliente(value.data.content)
    setTotalElements(value.data.totalElements)
  }
  const deleteColaboradorById = async (id: number) => {
    setIdCliente(id)
    setOpenModal(true)
  }

  const confirmaExclusao = async (confirm: boolean) => {
    if (idCliente && confirm) {
      api
        .delete(`/api/colaborador/deleteById/${idCliente}`)
        .then(response => {
          SuccessNotification({ message: response.data })
          findAllColaborador()
          setOpenModal(false)
        })
        .catch(error => {
          ErrorNotification({ message: error })
        })
    } else {
      setOpenModal(false)
    }
  }

  const validatePermissionRole = () => {
    if (Cookies.get('role') == 'CAIXA') {
      return true
    }
  }

  const columns = useMemo<MRT_ColumnDef<IColaborador>[]>(
    () => [
      {
        accessorKey: 'nome',
        header: t('pages.colaborador.table.nome'),
        enableSorting: true,
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
      {
        accessorKey: 'sobrenome',
        header: 'Sobrenome',
        enableSorting: true,
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
      {
        accessorKey: 'cpf',
        header: t('pages.colaborador.table.cpf'),
        enableSorting: true,
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
      {
        accessorKey: 'estado',
        header: 'Estado',
        enableSorting: true,
        filterVariant: 'select',
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
      {
        accessorKey: 'cidade',
        header: 'Cidade',
        enableSorting: true,
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
      {
        accessorKey: 'ativo',
        header: 'Ativo',
        filterVariant: 'select',
        enableSorting: true,
        enableColumnFilter: true,
        size: 15,
        minSize: 10,
        maxSize: 30,
        mantineTableBodyCellProps: {
          align: 'center',
        },
        mantineTableHeadCellProps: {
          align: 'center',
        },
      },
    ],
    [t]
  )

  const editar = (id: number) => {
    navigate.push(`colaborador/editar/${id}`)
  }

  const renderDetailPanel = ({ row }: { row: MRT_Row<IColaborador> }) => {
    return (
      <Flex>
        <Box
          w={'50%'}
          sx={{
            display: 'flex',
            justifyContent: 'flex-start',
            alignItems: 'center',
            gap: '16px',
            padding: '16px',
          }}
        >
          <Image
            radius="md"
            width={200}
            height={120}
            src={null}
            alt="With default placeholder"
            withPlaceholder
          />
          <Box sx={{ textAlign: 'center' }}>
            <Text fw={'bold'}>
              {row.original.nome} {row.original.sobrenome}
            </Text>
          </Box>
        </Box>
        <Divider size="sm" orientation="vertical" />
        <Flex direction={'column'} w={'50%'}>
          <Text align="center" fw={'bold'}>
            Dados Pessoais
          </Text>
          <Divider size="sm" mb={'0.5rem'} />
          <Flex align={'start'} direction={'column'} ml={'1rem'}>
            <Group>
              <Text fw={'bold'}>Cpf:</Text>
              <Text>{row.original.cpf}</Text>
            </Group>
            <Group>
              <Text fw={'bold'}>Data nascimento:</Text>
              <Text>
                {row.original.dataNascimento
                  ? row.original.dataNascimento
                  : '-'}
              </Text>
            </Group>
            <Group>
              <Text fw={'bold'}>Data da contratação:</Text>
              <Text>
                {row.original.dataContratoInicial
                  ? row.original.dataContratoInicial
                  : '-'}
              </Text>
            </Group>
            <Group>
              <Text fw={'bold'}>Data de demissão:</Text>
              <Text>
                {row.original.dataContratoFinal
                  ? row.original.dataContratoFinal
                  : '-'}
              </Text>
            </Group>
            <Group>
              <Text fw={'bold'}>Status:</Text>
              <Text>{row.original.ativo ? row.original.ativo : '-'}</Text>
            </Group>
            {(Cookies.get('role') == 'ADMIN' ||
              Cookies.get('role') == 'PROPRIETARIO') && (
              <Group>
                <Text fw={'bold'}>Sálario:</Text>
                <Text>
                  {row.original.salario
                    ? row.original.salario.toLocaleString('pt-BR', {
                        style: 'currency',
                        currency: 'BRL',
                      })
                    : '-'}
                </Text>
              </Group>
            )}
          </Flex>
        </Flex>
        <Divider size="sm" orientation="vertical" />
        <Flex direction={'column'} w={'50%'}>
          <Text fw={'bold'} align="center">
            Endereço
          </Text>
          <Divider size="sm" mb={'0.5rem'} />
          <Flex align={'start'} direction={'column'} ml={'1rem'}>
            <Flex>
              <Text fw={'bold'}>Cidade:</Text>
              <Text ml={'0.5rem'}>{row.original.cidade}</Text>
            </Flex>
            <Flex>
              <Text fw={'bold'}>Bairro:</Text>
              <Text ml={'0.5rem'}>
                {row.original.bairro ? row.original.bairro : '-'}
              </Text>
            </Flex>
            <Flex>
              <Text fw={'bold'}>Estado:</Text>
              <Text ml={'0.5rem'}>
                {row.original.estado ? row.original.estado : '-'}
              </Text>
            </Flex>
            <Flex>
              <Text fw={'bold'}>Cep:</Text>
              <Text ml={'0.5rem'}>
                {row.original.cep ? formataCep(row.original.cep) : '-'}
              </Text>
            </Flex>
          </Flex>
        </Flex>
        <Divider size="sm" orientation="vertical" />
        <Flex direction={'column'} w={'50%'}>
          <Text fw={'bold'} align="center">
            Contato
          </Text>
          <Divider size="sm" mb={'0.5rem'} />
          <Flex align={'start'} direction={'column'} ml={'1rem'}>
            <Flex>
              <Text fw={'bold'}>E-mail:</Text>
              <Text ml={'0.5rem'}>{row.original.email}</Text>
            </Flex>
            <Flex>
              <Text fw={'bold'}>Telefone:</Text>
              <Text ml={'0.5rem'}>
                {row.original.telefone
                  ? formatarTelefone(row.original.telefone)
                  : '-'}
              </Text>
            </Flex>
          </Flex>
        </Flex>
      </Flex>
    )
  }

  const rowActions = ({ row }: { row: MRT_Row<IColaborador> }) => (
    <Flex>
      <Tooltip label={t('pages.fornecedor.tooltip.editar')}>
        <ActionIcon
          disabled={validatePermissionRole()}
          size="sm"
          color="blue"
          variant="transparent"
          aria-label="Settings"
          onClick={() => editar(row.original.id!)}
        >
          <IconEdit />
        </ActionIcon>
      </Tooltip>
      <Tooltip label={t('pages.fornecedor.tooltip.excluir')}>
        <ActionIcon
          disabled={validatePermissionRole()}
          size="sm"
          color="red"
          variant="transparent"
          aria-label="Settings"
          onClick={() => deleteColaboradorById(row.original.id!)}
        >
          <IconTrash />
        </ActionIcon>
      </Tooltip>
    </Flex>
  )

  return (
    <>
      <Flex justify={'space-between'} align={'center'} m={10}>
        <Text fz={'1.5rem'} fw={'bold'}>
          Listagem de colaboradores
        </Text>
        <Button
          disabled={validatePermissionRole()}
          leftIcon={<IconUserPlus size={16} />}
          onClick={() => navigate.push('colaborador/cadastro')}
        >
          {t('pages.colaborador.buttonCadastro')}
        </Button>
      </Flex>
      <PaginationTable
        setSorting={setSorting}
        columns={columns}
        rowActions={rowActions}
        renderDetailPanel={renderDetailPanel}
        setPagination={setPagination}
        enableRowActions
        enableSorting
        enableClickToCopy
        onColumnFiltersChange={setColumnFilters}
        positionActionsColumn="last"
        data={dataCliente}
        state={{
          columnFilters,
          sorting,
          pagination: {
            pageIndex: filtro.pagina,
            pageSize: filtro.tamanhoPagina,
          },
        }}
        rowCount={totalElements}
      />
      <ModalColaborador
        openModal={openModal}
        title={t('pages.colaborador.modal.title')}
        textExclusao={t('pages.colaborador.modal.text')}
        idCliente={idCliente}
        confirmaExclusao={confirmaExclusao}
      />
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
