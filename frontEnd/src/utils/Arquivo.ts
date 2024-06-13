export const downloadByteArrayAsFile = (
  byteArray: Uint8Array,
  fileName: string
): void => {
  const blob = new Blob([byteArray])
  const url = URL.createObjectURL(blob)

  const a = document.createElement('a')
  a.style.display = 'none'
  a.href = url
  a.download = fileName

  document.body.appendChild(a)
  a.click()

  window.URL.revokeObjectURL(url)
  document.body.removeChild(a)
}

export const openByteArrayNewTab = (
  byteArray: Uint8Array,
  fileName: string
): void => {
  const type = getContentTypeFromFilename(fileName)
  const blob = new Blob([byteArray], { type })
  const url = URL.createObjectURL(blob)

  const newTab = window.open(url, '_blank')
  if (newTab) newTab.focus()
}

export const getContentTypeFromFilename = (filename: string): string => {
  const lastDotIndex = filename.lastIndexOf('.')
  if (lastDotIndex !== -1) {
    const extension = filename.substring(lastDotIndex + 1).toLowerCase()

    switch (extension) {
      case 'pdf':
        return 'application/pdf'
      case 'jpg':
      case 'jpeg':
        return 'image/jpeg'
      case 'png':
        return 'image/png'
      case 'zip':
        return 'application/zip'
      default:
        return 'application/octet-stream'
    }
  } else {
    return 'application/octet-stream'
  }
}
