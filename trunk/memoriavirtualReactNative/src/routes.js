import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';

import Home from './screens/Main';

const MainNavigator = createStackNavigator({
  Home: { screen : Home },
}, {
  initialRouteName: 'Home',
});

const appContainer = createAppContainer(MainNavigator);

export default appContainer;