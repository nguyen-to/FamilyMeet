import { createFileRoute } from '@tanstack/react-router'
import FamilyMeetRegister from '../../page/registerPage/RegisterPage'

export const Route = createFileRoute('/auth/register')({
  component: FamilyMeetRegister,
})


