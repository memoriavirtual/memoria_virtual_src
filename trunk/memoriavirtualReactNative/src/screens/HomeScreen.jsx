import React from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  TextInput,
} from 'react-native';
import { Icon } from 'react-native-elements';

import homeStyles from '../styles/home';
import CustomButton from '../components/CustomButton';
import VerticalSpacer from '../components/VerticalSpacer';

const styles = StyleSheet.create({
  ...homeStyles(),
});

const footnoteFirst = 'Copyright © 2009 LABES';
const footnoteSecond = 'Laboratório de Engenharia de Software - ICMC-USP';

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
      <Text style={styles.footnoteText}>{footnoteFirst}{'\n'}{footnoteSecond}</Text>
    </View>
);

HomeScreen.navigationOptions = () => ({
  headerTitle: 'Busca',
});

export default HomeScreen;
