import React from 'react';
import Routes from './routes';

import { Provider as I18nProvider } from './context/I18nContext';

const App = () => (
  <I18nProvider>
    <Routes />
  </I18nProvider>
);

export default App;
