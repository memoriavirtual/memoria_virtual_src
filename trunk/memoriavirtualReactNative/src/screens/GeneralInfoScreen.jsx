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
            <View style={styles.formInput}>
              <Text style={styles.formTitleText}>{isSwitchOn ? 'Sim' : 'Não'}</Text>
              <Switch
                trackColor={{ false: '#767577', true: '#81b0ff' }}
                onValueChange={onToggleSwitch}
                value={isSwitchOn}
                disabled
              />
            </View>
          </View>
          <View style={styles.inputs}>
            <View style={styles.formTitle}>
              <Text style={styles.formTitleText}>Revisão</Text>
            </View>

            <View style={styles.formInput}>
              <Text style={styles.formTitleText}>{isSwitchOnReview ? 'Sim' : 'Não'}</Text>
              <Switch
                trackColor={{ false: '#767577', true: '#81b0ff' }}
                onValueChange={onToggleSwitchReview}
                value={isSwitchOnReview}
                disabled
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

          <CustomTextInfo
            infoTitle="Longitude"
            infoValue="-"
          />
      </View>
      <Footnote />
    </View>
  );
};

GeneralInfoScreen.navigationOptions = () => ({
  headerTitle: 'Informações Gerais',
});

export default GeneralInfoScreen;
