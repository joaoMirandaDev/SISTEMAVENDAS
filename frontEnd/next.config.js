const { i18n } = require('./next-i18next.config')

module.exports = {
  i18n,
  compiler: {
    styledComponents: true
  },
  experimental: {
    newNextLinkBehavior: true,
  },
}
