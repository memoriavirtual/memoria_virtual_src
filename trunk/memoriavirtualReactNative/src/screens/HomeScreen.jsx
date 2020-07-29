import React from 'react';
import {
  View,
  Image,
  StyleSheet,
  TextInput,
} from 'react-native';
import { Icon } from 'react-native-elements';

import homeStyles from '../styles/home';
import CustomButton from '../components/CustomButton';
import VerticalSpacer from '../components/VerticalSpacer';
import Footnote from '../components/Footnote';

const styles = StyleSheet.create({
  ...homeStyles(),
});

const HomeScreen = ({ navigation }) => (
    <View style={styles.mainContainer}>
      <Image
        style={styles.logoImage}
        source={require('../assets/logo_mv_catalogacao1.png')}
      />
      <View style={styles.searchInputContainer}>
        <TextInput
          autoCorrect={false}

        />
      </View>
      <VerticalSpacer />
      <CustomButton
        buttonLabel="BUSCAR"
        icon={
          <Icon
            name="search"
            type="material"
            color="white"
            size={28}
          />
        }
        action={() => navigation.navigate('Search')}
      />
      <Footnote />
    </View>
);

HomeScreen.navigationOptions = () => ({
  headerTitle: 'Busca',
});

export default HomeScreen;
