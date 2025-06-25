import { createFileRoute } from '@tanstack/react-router'
import FeaturePage from '../page/featurePage/FeaturePage'

export const Route = createFileRoute('/feature')({
  component: FeaturePage,
})


