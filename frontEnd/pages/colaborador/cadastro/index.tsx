import CadastroCpf from '@components/pages/colaborador/cadastro/cpf'
import { Card, Divider, Title } from '@mantine/core'
import { useTranslate } from '@refinedev/core'
import { GetServerSideProps } from 'next'
import { serverSideTranslations } from 'next-i18next/serverSideTranslations'

export default function ColaboradorCadastro() {
  const t = useTranslate()

  return (
    <>
      <Card>
        <Title fw={700} size="xl">
          {t('pages.colaborador.title')}
        </Title>
        <Divider my="md" />
        {<CadastroCpf />}
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
