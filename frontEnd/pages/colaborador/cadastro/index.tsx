import CadastroCpf from '@components/pages/colaborador/cadastro/cpf'
import { Card, Text } from '@mantine/core'
import { useTranslate } from '@refinedev/core'
import { GetServerSideProps } from 'next'
import { serverSideTranslations } from 'next-i18next/serverSideTranslations'

export default function ColaboradorCadastro() {
  const t = useTranslate()

  return (
    <>
      <Text fz={'1.5rem'} m={'1rem'} fw={'bold'}>
        {t('pages.colaborador.cadastro.title')}
      </Text>
      <Card shadow="sm" padding="lg" radius="md" withBorder>
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
