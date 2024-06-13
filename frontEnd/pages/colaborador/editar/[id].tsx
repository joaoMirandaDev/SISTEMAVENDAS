import EditarColaborador from '@components/pages/colaborador/editar'
import { Title, Card, Divider } from '@mantine/core'
import { useTranslate } from '@refinedev/core'
import { GetServerSideProps } from 'next'
import { serverSideTranslations } from 'next-i18next/serverSideTranslations'
import { useRouter } from 'next/router'
export default function EditarFornecedor() {
  const router = useRouter()
  const t = useTranslate()
  const { id } = router.query

  return (
    <>
      <Card>
        <Title fw={700} size="xl">
          {t('pages.fornecedor.titleEdit')}
        </Title>
        <Divider my="md" />
        <EditarColaborador idColaborador={id} />
      </Card>
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
