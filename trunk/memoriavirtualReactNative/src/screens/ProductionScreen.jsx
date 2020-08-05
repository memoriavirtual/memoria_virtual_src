import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
} from 'react-native';

import productionStyles from '../styles/production';
import CustomTextInfo from '../components/CustomTextInfo';
import Title from '../components/Title';
import Footnote from '../components/Footnote';

const styles = StyleSheet.create({
  ...productionStyles(),
});

const ProductionScreen = () => (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <CustomTextInfo
            infoTitle="Local"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Ano"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Edição"
            infoValue="-"
          />

          <View style={styles.inputs}>

            <View style={styles.lastFormTitle}>
              <Text style={styles.lastFormTitleText}
              >Outras responsibilidades</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
                value="-"
              />
            </View>
          </View>
      </View>
    </View>
);

ProductionScreen.navigationOptions = () => ({
  headerTitle: 'Produção',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default ProductionScreen;
