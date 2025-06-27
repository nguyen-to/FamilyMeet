import { createFileRoute } from '@tanstack/react-router'
import FamilyDashboard from '../../page/dashboard/FamilyDashboard'

export const Route = createFileRoute('/_familylayout/_familylayout')({
  component: FamilyDashboard,
})


