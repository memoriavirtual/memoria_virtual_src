import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import Main from './pages/main';


const rootStack = createStackNavigator({
    Main
});

const appContainer = createAppContainer(rootStack);

export default appContainer;