import { createFileRoute } from '@tanstack/react-router'
import FamilyMeetLogin from '../../page/loginPage/LoginPage'

export const Route = createFileRoute('/auth/login')({
  component: FamilyMeetLogin,
})


