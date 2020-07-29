import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  Switch,
} from 'react-native';

import generalinfoStyles from '../styles/generalinfo';
import CustomTextInfo from '../components/CustomTextInfo';
import Title from '../components/Title';
import Footnote from '../components/Footnote';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});


const GeneralInfoScreen = () => {
  const [isSwitchOn, setIsSwitchOn] = useState(false);
  const onToggleSwitch = () => setIsSwitchOn(!isSwitchOn);

  const [isSwitchOnReview, setIsSwitchOnReview] = useState(false);
  const onToggleSwitchReview = () => setIsSwitchOnReview(!isSwitchOnReview);
  
  return (
    <View style={styles.container}>
      <Title titleText="Sibipiruna" />
      <View style={styles.form}>

          <View style={styles.inputs}>

            <View style={styles.formTitle}>
              <Text style={styles.formTitleText}>Externo</Text>
            </View>

            <View
            style={styles.spacer}
            />

            <View style={styles.formInput}>
              <Text style={styles.formTitleText}>{isSwitchOn ? 'Sim' : 'Não'}</Text>
              <Switch
                trackColor={{ false: '#767577', true: '#81b0ff' }}
                onValueChange={onToggleSwitch}
                value={isSwitchOn}
              />
            </View>

          </View>

          <View style={styles.inputs}>

            <View style={styles.formTitle}>
              <Text style={styles.formTitleText}>Revisão</Text>
            </View>
            <View
              style={styles.spacer}
            />
            <View style={styles.formInput}>
              <Text style={styles.formTitleText}>{isSwitchOnReview ? 'Sim' : 'Não'}</Text>
              <Switch
                trackColor={{ false: '#767577', true: '#81b0ff' }}
                onValueChange={onToggleSwitchReview}
                value={isSwitchOnReview}
              />
            </View>

          </View>

          <CustomTextInfo
            infoTitle="Tipo"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Nº de registro"
            infoValue="MFF-FL-323"
          />

          <CustomTextInfo
            infoTitle="Título Principal"
            infoValue="Sibipiruna"
          />

          <CustomTextInfo
            infoTitle="Complemento"
            infoValue="-"
          />

          <CustomTextInfo
            infoTitle="Coleção"
            infoValue="Fabaceae"
          />

          <CustomTextInfo
            infoTitle="Latitude"
            infoValue="-"
          />

          <View style={styles.inputs}>
            <View style={styles.lastFormTitle}>
              <Text style={styles.formTitleText}
                value="-">Longitude</Text>
            </View>
            <View
            style={styles.spacer}
            />
            <View style={styles.formInput}>
              <TextInput
              />
            </View>
          </View>
      </View>
      <Footnote />
    </View>
  );
};

GeneralInfoScreen.navigationOptions = () => ({
  headerTitle: 'Informações Gerais',
  headerTitleStyle: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 22,
  },
  headerStyle: {
    backgroundColor: '#4488BB',
  },
});

export default GeneralInfoScreen;
