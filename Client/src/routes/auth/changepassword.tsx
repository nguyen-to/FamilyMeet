import { createFileRoute } from '@tanstack/react-router'
import ChangePassword from '../../page/changepassword/ChangePassword'

export const Route = createFileRoute('/auth/changepassword')({
  component: ChangePassword,
})

